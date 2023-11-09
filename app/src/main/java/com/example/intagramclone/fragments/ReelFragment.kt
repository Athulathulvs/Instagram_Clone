package com.example.intagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intagramclone.R
import com.example.intagramclone.adapter.ReelAdapter
import com.example.intagramclone.databinding.FragmentMyReelsBinding
import com.example.intagramclone.databinding.FragmentReelBinding
import com.example.intagramclone.models.Post
import com.example.intagramclone.models.Reel
import com.example.intagramclone.util.REEL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ReelFragment : Fragment() {
    private lateinit var binding: FragmentReelBinding
    lateinit var adapter:ReelAdapter
    var reelList =ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReelBinding.inflate(inflater,container,false)

        adapter = ReelAdapter(requireContext(),reelList)
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection(REEL).get()
            .addOnSuccessListener {
                var tempList =ArrayList<Reel>()
                reelList.clear()
                for (i in it.documents){
                    var reel =i.toObject<Reel>()!!
                    tempList.add(reel)
                }
                reelList.addAll(tempList)
                reelList.reverse()
                adapter.notifyDataSetChanged()
            }
        return binding.root
    }

    companion object {

    }
}