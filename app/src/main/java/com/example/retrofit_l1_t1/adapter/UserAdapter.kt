package com.example.retrofit_l1_t1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit_l1_t1.databinding.UserLayoutBinding
import com.example.retrofit_l1_t1.model.Data


class UserAdapter : ListAdapter<Data, UserAdapter.ListUsersViewHolder>(DiffCallBack()) {
    lateinit var onClick: (id: Int) -> Unit
    lateinit var onLongClick: (Int) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    inner class ListUsersViewHolder(private val binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            with(binding) {
                Glide.with(imageView)
                    .load(data.avatar)
                    .circleCrop()
                    .into(imageView)

                firstName.text = data.first_name
                lastName.text = data.last_name
                email.text = email.text
            }
            itemView.setOnLongClickListener {
                onLongClick(data.id)
                true
            }
            itemView.setOnClickListener {
                onClick(data.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUsersViewHolder {
        return ListUsersViewHolder(
            UserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListUsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}