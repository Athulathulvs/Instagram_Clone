package com.example.intagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.intagramclone.databinding.ActivityLogInBinding
import com.example.intagramclone.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {

        binding.logInBtn.setOnClickListener {
          if (  binding.userName.editText?.text.toString().isBlank()or
            binding.password.editText?.text.toString().isBlank() )
          {
              Toast.makeText(this, "Cant find account", Toast.LENGTH_SHORT).show()
          }else{
              var user = User(
                  binding.userName.editText?.text.toString(),
                  binding.password.editText?.text.toString())
              Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                  .addOnSuccessListener {
                      val intent = Intent(this,HomeActivity::class.java)
                      startActivity(intent)
                      finish()
                  }.addOnFailureListener {
                      Toast.makeText(this, ".e", Toast.LENGTH_SHORT).show()
                  }
          }
        }

        binding.createAccount.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}