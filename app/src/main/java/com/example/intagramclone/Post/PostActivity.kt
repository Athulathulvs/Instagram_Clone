package com.example.intagramclone.Post

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.intagramclone.HomeActivity
import com.example.intagramclone.databinding.ActivityPostBinding
import com.example.intagramclone.models.Post
import com.example.intagramclone.models.User
import com.example.intagramclone.util.POST
import com.example.intagramclone.util.POST_FOLDER
import com.example.intagramclone.util.USER_NODE
import com.example.intagramclone.util.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    lateinit var user: User
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
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
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.postBtn.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document().get()
                .addOnSuccessListener {
                    var user = it.toObject<User>()
                    val post: Post = Post(
                        postUrl = imageUrl!!,
                        caption = binding.caption.text.toString(),
                        uid = Firebase.auth.currentUser!!.uid,
                        time =System.currentTimeMillis().toString()
                    )
                    //editText?.text

                    Firebase.firestore.collection(POST).document().set(post)
                        .addOnSuccessListener {
                            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
                                .document()
                                .set(post)
                                .addOnSuccessListener {
                                    startActivity(Intent(this, HomeActivity::class.java))
                                    finish()
                                }
                        }
                }
        }
    }
}