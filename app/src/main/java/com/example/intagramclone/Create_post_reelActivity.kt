package com.example.intagramclone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intagramclone.Post.PostActivity
import com.example.intagramclone.databinding.ActivityCreatePostReelBinding

class Create_post_reelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostReelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreatePostReelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()

    }

    private fun setup() {
        binding.post.setOnClickListener {
            val intent =Intent(this,PostActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.reel.setOnClickListener {
            val intent =Intent(this,PostActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}