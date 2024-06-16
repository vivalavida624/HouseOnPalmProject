package com.map08.houseonpalm.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.map08.houseonpalm.models.Broker

class BrokerRepository {

    private val database = FirebaseFirestore.getInstance()

    fun addBroker(broker: Broker) {
        val brokersCollection = database.collection("brokers")
        val brokerId = brokersCollection.document().id
        val brokerWithId = broker.copy(id = brokerId)

        brokersCollection.document(brokerId).set(brokerWithId)
            .addOnSuccessListener {
                Log.d(TAG, "Broker added successfully: $broker")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding broker: $e")
            }
    }

    fun getBrokers(callback: (List<Broker>) -> Unit) {
        database.collection("brokers").get()
            .addOnSuccessListener { result ->
                val brokers = mutableListOf<Broker>()
                for (document in result) {
                    document.toObject<Broker>()?.let { broker ->
                        brokers.add(broker)
                    }
                }
                callback(brokers)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting brokers: $e")
            }
    }

    fun updateBroker(broker: Broker) {
        database.collection("brokers").document(broker.id).set(broker)
            .addOnSuccessListener {
                Log.d(TAG, "Broker updated successfully: ${broker.id}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating broker: $e")
            }
    }

    fun deleteBroker(brokerId: String) {
        database.collection("brokers").document(brokerId).delete()
            .addOnSuccessListener {
                Log.d(TAG, "Broker deleted successfully: $brokerId")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting broker: $e")
            }


    }

    companion object {
        private const val TAG = "BrokerRepository"
    }
}