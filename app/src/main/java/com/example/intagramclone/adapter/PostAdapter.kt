package com.example.intagramclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.intagramclone.R
import com.example.intagramclone.databinding.HomePostRvBinding
import com.example.intagramclone.models.Post
import com.example.intagramclone.models.User
import com.example.intagramclone.util.USER_NODE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostAdapter(var context: Context, var postList:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.MyHolder>() {
    inner class MyHolder( var binding:HomePostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
       var binding=HomePostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get()
                .addOnSuccessListener {
                    var user=it.toObject<User>()
                    Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.ProfilePic)
                    holder.binding.profileName.text =user.name
                }
        }catch (e:Exception){

        }

        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)
        holder.binding.caption.text=postList.get(position).caption
        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.heart_red)
        }
        holder.binding.share.setOnClickListener {
            var intent=Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(intent)
        }

    }
}