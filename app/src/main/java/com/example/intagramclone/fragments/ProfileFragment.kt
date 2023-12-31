package com.example.intagramclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.intagramclone.Create_post_reelActivity
import com.example.intagramclone.adapter.ViewPagerAdapter
import com.example.intagramclone.databinding.ActivityCreatePostReelBinding
import com.example.intagramclone.databinding.FragmentProfileBinding
import com.example.intagramclone.edt_ProfileActivity
import com.example.intagramclone.models.User
import com.example.intagramclone.util.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
   private lateinit var binding: FragmentProfileBinding
   private lateinit var viewPagerAdapter: ViewPagerAdapter
   private lateinit var create_postreel:ActivityCreatePostReelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        
        binding.prfAdd.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), Create_post_reelActivity::class.java))
            activity?.finish()
        }
        binding.edtBtn.setOnClickListener {
            var intent = Intent(view?.context,edt_ProfileActivity::class.java)
            intent.putExtra("MODE",1)
            startActivity(intent)
        }
        viewPagerAdapter=ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(),"My Post")
        viewPagerAdapter.addFragments(MyReelsFragment(),"My Reels")
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User =it.toObject<User>()!!
                binding.profileName.text =user.name
                binding.UserName.text=user.username
                if (!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.ProfilePic)
                }

            }

    }

}