package com.example.restaurantreview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantreview.data.response.DetailUserResponse
import com.example.restaurantreview.databinding.ItemUserBinding

class UserFollowAdapter(
    private val followers: List<DetailUserResponse>
) : ListAdapter<DetailUserResponse, UserFollowAdapter.MyViewHolder>(DIFF_CALLBACK){
    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DetailUserResponse) {
            binding.tvItem.text = "${user.login}"
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserFollowAdapter.MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return UserFollowAdapter.MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFollowAdapter.MyViewHolder, position: Int) {
        holder.bind(followers[position])
    }

    override fun getItemCount(): Int {
        return followers.size
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailUserResponse>(){
            override fun areItemsTheSame(
                oldItem: DetailUserResponse,
                newItem:DetailUserResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailUserResponse,
                newItem: DetailUserResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}