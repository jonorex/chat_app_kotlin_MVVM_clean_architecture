package com.joao.garageapp.ui.user.list_user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joao.garageapp.databinding.ItemUserBinding

import com.joao.garageapp.domain.model.User

class ListUserAdapter(private val onItemClicked: (User) -> Unit) : RecyclerView.Adapter<ListUsersViewHolder>(){

    private var users : List<User> = ArrayList()

    fun setUserList(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ListUsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUsersViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, onItemClicked)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class ListUsersViewHolder(
    private val binding : ItemUserBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(user: User, onItemClicked: (User) -> Unit) {
        binding.textEmail.text = user.email
        binding.textName.text = user.name

        Glide.with(itemView.context).load(user.image).into(binding.imageProfile)
        itemView.setOnClickListener {
            onItemClicked(user)
        }
    }
}