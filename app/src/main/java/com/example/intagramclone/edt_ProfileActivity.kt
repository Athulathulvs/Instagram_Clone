package com.example.intagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intagramclone.databinding.ActivityEdtProfileBinding

class edt_ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdtProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdtProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}