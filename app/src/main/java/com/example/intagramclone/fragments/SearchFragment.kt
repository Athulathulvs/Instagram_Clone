package com.example.intagramclone.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intagramclone.adapter.SearchAdapter
import com.example.intagramclone.databinding.FragmentSearchBinding
import com.example.intagramclone.models.User
import com.example.intagramclone.util.USER_NODE
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var searchResults = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  searchCity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchRecycleV.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext(), searchResults)
        binding.searchRecycleV.adapter = adapter

//        binding.searchView.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged( charSequence: CharSequence?, start: Int, before: Int, count: Int) {
//                val searchTerm = charSequence.toString().trim()
//                performSearch(searchTerm)
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//        })
//        return binding.root
//    }
//    private fun performSearch(searchTerm: String) {
//        val collectionReference =  Firebase.firestore.collection(USER_NODE)
//
//        // Query for documents where a specific field contains the search term
//        val query: Query = collectionReference.whereEqualTo("name", searchTerm)
//
//        // Add more conditions or customize the query as needed
//
//        query.get()
//            .addOnSuccessListener { documents ->
//                // Convert documents to your data model
//                val searchResults = documents.toObjects(User::class.java)
//
//                // Update the adapter with the search results
//                adapter.setData(searchResults as ArrayList<User?>)
//            }
//            .addOnFailureListener { exception ->
//                // Handle errors
//            }
//    }
        // new testing
                binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged( charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.searchView.text.toString().isNotEmpty()){
                    binding.searchRecycleV.visibility = View.VISIBLE
                    retriveUsers(charSequence.toString())
                }else{
                    binding.searchRecycleV.visibility = View.GONE
                    searchResults.clear()
                    adapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    private fun retriveUsers(searchQuery: String) {

            // Query the Firestore users collection based on the searchQuery
        val userRef= Firebase.firestore.collection(USER_NODE)
                .whereGreaterThanOrEqualTo("name", searchQuery)
                .whereLessThanOrEqualTo("name", searchQuery + "\uf8ff")
                .limit(20)
                userRef.addSnapshotListener { queryDocumentSnapshots, e ->
                    if (e != null) {
                        // Handle the error
                        return@addSnapshotListener
                    }

                    // Clear the previous search results
                    searchResults.clear()

                    // Iterate through the query results and update the searchResults list
                    for (documentSnapshot: QueryDocumentSnapshot in queryDocumentSnapshots!!) {
                        val username = documentSnapshot.toObject(User::class.java)
                        searchResults.add(username)
                    }

                    // Notify the adapter that the data set has changed
                    adapter.notifyDataSetChanged()
                }
    }
}