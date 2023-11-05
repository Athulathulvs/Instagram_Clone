package com.example.intagramclone.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.intagramclone.HomeActivity

import com.example.intagramclone.databinding.ActivityPostBinding
import com.example.intagramclone.models.Post
import com.example.intagramclone.models.User
import com.example.intagramclone.util.POST
import com.example.intagramclone.util.POST_FOLDER
import com.example.intagramclone.util.USER_PROFILE
import com.example.intagramclone.util.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    lateinit var user:User
    var imageUrl:String?=null
    private val launcher =registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER){
                url->
                if(url!=null){
                    binding.selectImage.setImageURI(uri)
                    imageUrl=url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materilToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materilToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        binding.postBtn.setOnClickListener {
            //editText?.text
            val post:Post= Post(imageUrl!!,binding.caption.text.toString())
            Firebase.firestore.collection(POST).document().set(post)
                .addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post)
                        .addOnSuccessListener {
                            startActivity(Intent(this,HomeActivity::class.java))
                            finish()
                        }
                }
        }
    }
}