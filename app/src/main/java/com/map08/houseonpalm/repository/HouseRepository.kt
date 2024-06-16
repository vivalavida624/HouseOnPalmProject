package com.map08.houseonpalm.repository

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

import com.map08.houseonpalm.models.House



class HouseRepository {

    private val database = FirebaseFirestore.getInstance()

    fun addHouse(house: House) {
        val housesCollection = database.collection("houses")
        val houseId = housesCollection.document().id
        val houseWithId = house.copy(id = houseId)

        housesCollection.document(houseId).set(houseWithId)
            .addOnSuccessListener {
                Log.d(TAG, "Houses added successfully: $house")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding houses: $e")
            }
    }

    fun gethouses(callback: (List<House>) -> Unit) {
        database.collection("houses").get()
            .addOnSuccessListener { result ->
                val houses = mutableListOf<House>()
                for (document in result) {
                    document.toObject<House>()?.let { house ->
                        houses.add(house)
                    }
                }
                callback(houses)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting houses: $e")
            }
    }

    fun updateHouses(houses: House) {
        database.collection("houses").document(houses.id).set(houses)
            .addOnSuccessListener {
                Log.d(TAG, "Houses updated successfully: ${houses.id}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating houses: $e")
            }
    }

    fun deleteHouses(housesId: String) {
        database.collection("houses").document(housesId).delete()
            .addOnSuccessListener {
                Log.d(TAG, "Houses deleted successfully: $housesId")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting houses: $e")
            }


    }

    companion object {
        private const val TAG = "HousesRepository"
    }
}