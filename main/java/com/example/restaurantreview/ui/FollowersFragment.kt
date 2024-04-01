package com.example.restaurantreview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.response.DetailUserResponse
import com.example.restaurantreview.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var viewModel: DetailUserViewModel
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var position: Int = 0
        var username: String = ""

        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
            username = it.getString(ARG_USERNAME, "")
        }

        viewModel = ViewModelProvider(requireActivity()).get(DetailUserViewModel::class.java)

        if (position == 1){
            viewModel.findUserFollowers(username)
        } else {
            viewModel.findUserFollowing(username)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowers.layoutManager = layoutManager
        binding.rvFollowers.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager(requireContext()).orientation
            )
        )

        viewModel.userFollowers.observe(viewLifecycleOwner, {followers ->
            if (position == 1) {  // Observe followers if position is 1
                showUser(followers)
            }
        })

        viewModel.userFollowing.observe(viewLifecycleOwner, {following ->
            if (position == 2) {  // Observe following if position is 2
                showUser(following)
            }
        })

        viewModel.isLoadingFollowers.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.isLoadingFollowing.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

    }


    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private fun showUser(followers: List<DetailUserResponse>){
        if (followers.isEmpty()) {
            binding.rvFollowers.visibility = View.VISIBLE
        } else {
            val adapter = UserFollowAdapter(followers)
            binding.rvFollowers.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}