package com.example.intagramclone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intagramclone.databinding.ActivityEdtProfileBinding
import com.example.intagramclone.models.User
import com.example.intagramclone.util.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class edt_ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdtProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdtProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveAcc.setOnClickListener {

        }
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User=it.toObject<User>()!!
                binding.fullName.setText(user.name)
                binding.userName.setText(user.username)
                binding.bio.setText(user.bio)
                binding.gender.setText(user.gender)
                if (!user.image.isNullOrEmpty()){
                   Picasso.get().load(user.image).into(binding.ProfileName)
               }
            }
    }
}