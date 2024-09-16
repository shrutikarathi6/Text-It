package com.example.text_it.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.text_it.R
import com.example.text_it.adapter.CallAdapter
import com.example.text_it.dataClass.CallInfo
import com.google.firebase.firestore.FirebaseFirestore




class Call : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CallAdapter
    private val callList = mutableListOf<CallInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_call, container, false)

        val searchButton: ImageButton = view.findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            val searchFragment = Search()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, searchFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        val callButton : ImageButton = view.findViewById(R.id.callButton)


        callButton.setOnClickListener {
            val searchFragment = Search()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, searchFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        recyclerView = view.findViewById(R.id.recyclerViewCallList)
        adapter = CallAdapter(requireContext(),callList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchDataFromFirebase()

        return view
    }

    private fun fetchDataFromFirebase() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("USERS").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val name = document.data["name"].toString()
                    val phone = document.data["phone"].toString()
                    val profileImage = document.data["profileImage"].toString()
                    val call = CallInfo(name,phone,profileImage)
                    callList.add(call)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.d("Call", "Error getting documents: ", exception)
            }
    }
}

