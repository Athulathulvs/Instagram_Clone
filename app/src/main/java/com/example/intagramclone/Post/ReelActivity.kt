package com.example.intagramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.intagramclone.HomeActivity
import com.example.intagramclone.databinding.ActivityReelBinding
import com.example.intagramclone.models.Reel
import com.example.intagramclone.models.User
import com.example.intagramclone.util.POST_FOLDER
import com.example.intagramclone.util.REEL
import com.example.intagramclone.util.REEL_FOLDER
import com.example.intagramclone.util.USER_NODE
import com.example.intagramclone.util.uploadImage
import com.example.intagramclone.util.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ReelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReelBinding
    private lateinit var  VideoUrl:String
    lateinit var progressDialog:ProgressDialog
    private val launcher =registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER,progressDialog){
                    url->
                if(url!=null){
                    VideoUrl=url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReelBinding.inflate(layoutInflater)
        setContentView(binding.root)
         progressDialog=ProgressDialog(this)

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.postBtn.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user:User =it.toObject<User>()!!
                    //editText?.text
                    val reel: Reel = Reel(VideoUrl!!,binding.caption.text.toString(),user.image!!)
                    Firebase.firestore.collection(REEL).document().set(reel)
                        .addOnSuccessListener {
                            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel)
                                .addOnSuccessListener {
                                    startActivity(Intent(this,HomeActivity::class.java))
                                    finish()
                                }
                        }
                }

        }
    }
}