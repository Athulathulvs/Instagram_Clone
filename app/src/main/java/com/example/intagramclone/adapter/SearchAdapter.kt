package com.example.intagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.intagramclone.R
import com.example.intagramclone.databinding.SearchRvBinding
import com.example.intagramclone.fragments.ProfileFragment
import com.example.intagramclone.models.User
import com.example.intagramclone.util.FOLLOW
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchAdapter(var context: Context, var userList:ArrayList<User>):RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
//    private var data: ArrayList<User> = ArrayList()
//    private var firebaseUser:FirebaseFirestore?=null
    inner class ViewHolder(var binding:SearchRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var binding=SearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(userList.get(position).image).placeholder(R.drawable.user).into(holder.binding.ProfilePic)
        holder.binding.profileName.text=userList.get(position).name
        holder.binding.UserName.text=userList.get(position).username
        val userId = Firebase.auth.currentUser?.uid
        holder.itemView.setOnClickListener {
            val pref =context.getSharedPreferences("PREF",Context.MODE_PRIVATE).edit()
            pref.putString("profileID",User.getUID())
            pref.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,ProfileFragment()).commit()
        }

        holder.binding.followBtn.setOnClickListener {
            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document()
                .set(userList.get(position))
            holder.binding.followBtn.text="Following"
        }



            }

}
///////
//        holder.binding.followBtn.setOnClickListener {
//            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
//            val followedUserId = userList[position].uid // Assuming there's a UID property in your User class
//
//            if (currentUserId != null && followedUserId != null) {
//                val currentFollowingRef = Firebase.firestore.collection("users").document(currentUserId)
//                    .collection("following").document(followedUserId)
//
//                // Check if the current user is already following
//                currentFollowingRef.get().addOnSuccessListener { currentFollowingSnapshot ->
//                    if (!currentFollowingSnapshot.exists()) {
//                        // If not following, update the current user's following list
//                        currentFollowingRef.set(userList[position])
//                            .addOnSuccessListener {
//                                // Handle success, you may want to update UI here
//                                holder.binding.followBtn.text = "Following"
//                            }
//                            .addOnFailureListener { e ->
//                                // Handle failure
//                            }
//
//                        // Update the followed user's followers list
//                        val followedFollowersRef = Firebase.firestore.collection("users").document(followedUserId)
//                            .collection("followers").document(currentUserId)
//
//                        followedFollowersRef.set(userList[position])
//                            .addOnSuccessListener {
//                                // Handle success, you may want to update UI here
//                            }
//                            .addOnFailureListener { e ->
//                                // Handle failure
//                            }
//                    } else {
//                        // The current user is already following the user
//                        // You may want to implement an "Unfollow" logic here
//                    }
//                }.addOnFailureListener {
//                    // Handle failure to check if the current user is already following
//                }
//            } else {
//                // Handle the case where there is no authenticated user or followed user ID is empty
//            }
//        }

////
//holder.binding.followBtn.setOnClickListener {
//    if (holder.binding.followBtn.text.toString() == "Follow") {
//        val uid = Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
//        db.collection("follow").document(uid.toString()).collection("following").document(user.getUID)
//            .set(mapOf("following" to true))
//            .addOnSuccessListener {
//                // Handle success
//            }
//            .addOnFailureListener { e ->
//                // Handle failure
//            }
//    }
//}
//val docRef = Firebase.firestore.collection("users").document("some-user-id")
//docRef.get().addOnSuccessListener { document ->
//    if (document != null) {
//        val user = document.toObject(User::class.java)
//        // Now you can use the 'user' object
//    } else {
//        Log.d(ContentValues.TAG, "No such document")
//    }
//}.addOnFailureListener { exception ->
//    Log.d(ContentValues.TAG, "get failed with ", exception)
//}
//            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
//            val followedUserId = User.getUID // Assuming getUID is a method to get the user ID
//
//            if (currentUserId != null && followedUserId != null) {
//                // Update the followed user's followers
//                FirebaseFirestore.getInstance().collection("users").document(followedUserId)
//                    .update("followers", FieldValue.arrayUnion(currentUserId))
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            // Update the current user's following
//                            FirebaseFirestore.getInstance().collection("users").document(currentUserId)
//                                .update("following", FieldValue.arrayUnion(followedUserId))
//                                .addOnCompleteListener { innerTask ->
//                                    if (innerTask.isSuccessful) {
//                                        // Follow operation successful
//                                        // You can add UI updates or other logic here
//                                    } else {
//                                        // Handle failure to update current user's following
//                                        Log.e("Follow", "Failed to update current user's following: ${innerTask.exception}")
//                                    }
//                                }
//                        } else {
//                            // Handle failure to update followed user's followers
//                            Log.e("Follow", "Failed to update followed user's followers: ${task.exception}")
//                        }
//                    }
//            }
//            privitr var firebasedatabase:firebaseUser?=null
//            holder.followBtn.setOnClickListener {
//                if (holder.followBtn.text.tostring()="Follow"){
//                    FirebaseUser.uid.let it1 ->
//                        Firebasedatabase.getinstance().regerence
//                            .child("follow").child(users.getUID)
//                            .child("following").child(it1.toString)
//                            .setValue(true).addOnCompletListener{
//                                    task->
//                                if (task.isSuccessful){
//                                    Firebase.firestore.uid.let{it1 ->
//                                        Firebasedatabase.getinstance().regerence
//                                            .child("follow").child(it1.toString)
//                                            .child("following").child(users.getUID)
//                                            .setValue(true).addOnCompletListener {
//
//                                            }    }
//                                }
//                            }
//                    }
//                    else{
//
//                    }
//
//                }