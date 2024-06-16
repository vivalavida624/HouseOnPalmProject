package com.map08.houseonpalm.repository

import android.util.Log
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
                Log.d(TAG, "House added successfully: $house")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding house: $e")
            }
    }

    fun getHouses(callback: (List<House>) -> Unit) {
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

    fun updateHouse(house: House) {
        database.collection("houses").document(house.id).set(house)
            .addOnSuccessListener {
                Log.d(TAG, "House updated successfully: ${house.id}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating house: $e")
            }
    }

    fun deleteHouse(houseId: String) {
        database.collection("houses").document(houseId).delete()
            .addOnSuccessListener {
                Log.d(TAG, "House deleted successfully: $houseId")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting house: $e")
            }
    }

    companion object {
        private const val TAG = "HouseRepository"
    }
}