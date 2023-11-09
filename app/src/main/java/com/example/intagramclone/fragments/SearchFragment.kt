package com.example.intagramclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intagramclone.adapter.SearchAdapter
import com.example.intagramclone.databinding.FragmentSearchBinding
import com.example.intagramclone.models.User
import com.example.intagramclone.util.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList=ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //  searchCity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchRview.layoutManager=LinearLayoutManager(requireContext())
        adapter= SearchAdapter(requireContext(),userList)
        binding.searchRview.adapter=adapter

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList=ArrayList<User>()
            userList.clear()
            for (i in it.documents){
                if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                }else{
                    var user:User=i.toObject<User>()!!
                    tempList.add(user)
                }

            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
    // sear not set
//    private fun searchCity() {
//        val searchview = binding.searchView
//        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query != null) {
//                    Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
//                        var tempList=ArrayList<User>()
//                        userList.clear()
//                        for (i in it.documents){
//                            if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){
//
//                            }else{
//                                var user:User=i.toObject<User>()!!
//                                tempList.add(user)
//                            }
//
//                        }
//                        userList.addAll(tempList)
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//
//        })
//    }

    companion object {
    }
}