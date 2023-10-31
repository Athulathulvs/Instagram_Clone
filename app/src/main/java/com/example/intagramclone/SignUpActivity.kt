package com.example.intagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.HomeScreenActivity
import com.example.intagramclone.databinding.ActivitySignUpBinding
import com.example.intagramclone.models.User
import com.example.intagramclone.util.USER_NODE
import com.example.intagramclone.util.USER_PROFILE
import com.example.intagramclone.util.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var  user : User
    private val launcher =registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri?.let {
              uploadImage(uri, USER_PROFILE){
                  if(it==null){

                  }else{
                      user.image=it
                      binding.addImg.setImageURI(uri)
                  }
              }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        user = User()
        binding.signUpBtn.setOnClickListener {
            if (binding.regUsername.editText?.text.toString().isBlank()or
                binding.regEmail.editText?.text.toString().isBlank()or
                binding.regPassword.editText?.text.toString().isBlank())
            {
                Toast.makeText(this, "Please fill the information ", Toast.LENGTH_SHORT).show()
            }else{

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.regEmail.editText?.text.toString(),
                    binding.regPassword.editText?.text.toString()
                ).addOnSuccessListener {
                    user.name = binding.regUsername.editText?.text.toString()
                    user.email = binding.regEmail.editText?.text.toString()
                    user.password = binding.regPassword.editText?.text.toString()
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                    Toast.makeText(this, "Registartion Sucessfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HomeScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }

            }
        }
        binding.addImg.setOnClickListener {
            launcher.launch("image/*")
        }
    }
}