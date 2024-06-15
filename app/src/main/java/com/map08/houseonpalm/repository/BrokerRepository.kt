package com.map08.houseonpalm.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.map08.houseonpalm.models.Broker

class BrokerRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun addBroker(broker: Broker) {
        val brokerId = database.child("brokers").push().key
        brokerId?.let {
            database.child("brokers").child(it).setValue(broker.copy(id = it))
                .addOnSuccessListener {
                    Log.d(TAG, "Broker added successfully: $broker")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error adding broker: $e")
                }
        }
    }

    fun getBrokers(callback: (List<Broker>) -> Unit) {
        database.child("brokers").get().addOnSuccessListener { snapshot ->
            val brokers = mutableListOf<Broker>()
            snapshot.children.forEach { childSnapshot ->
                childSnapshot.getValue(Broker::class.java)?.let {
                    brokers.add(it)
                }
            }
            callback(brokers)
        }
    }

    fun updateBroker(broker: Broker) {
        database.child("brokers").child(broker.id).setValue(broker)
    }

    fun deleteBroker(brokerId: String) {
        database.child("brokers").child(brokerId).removeValue()
    }
}