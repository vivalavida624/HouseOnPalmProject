package com.map08.houseonpalm.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.map08.houseonpalm.models.Favourite

class FavouriteRepository {

    private val database = FirebaseFirestore.getInstance()

    fun addFavourite(favourite: Favourite) {
        val favouritesCollection = database.collection("favourites")
        val favouriteId = favouritesCollection.document().id
        val favouriteWithId = favourite.copy(id = favouriteId)

        favouritesCollection.document(favouriteId).set(favouriteWithId)
            .addOnSuccessListener {
                Log.d(TAG, "Favourite added successfully: $favourite")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding favourite: $e")
            }
    }

    fun getFavourites(callback: (List<Favourite>) -> Unit) {
        database.collection("favourites").get()
            .addOnSuccessListener { result ->
                val favourites = mutableListOf<Favourite>()
                for (document in result) {
                    document.toObject<Favourite>()?.let { favourite ->
                        favourites.add(favourite)
                    }
                }
                callback(favourites)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting favourites: $e")
            }
    }

    fun updateFavourite(favourite: Favourite) {
        database.collection("favourites").document(favourite.id).set(favourite)
            .addOnSuccessListener {
                Log.d(TAG, "Favourite updated successfully: ${favourite.id}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating favourite: $e")
            }
    }

    fun deleteFavourite(favouriteId: String) {
        database.collection("favourites").document(favouriteId).delete()
            .addOnSuccessListener {
                Log.d(TAG, "Favourite deleted successfully: $favouriteId")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting favourite: $e")
            }
    }

    companion object {
        private const val TAG = "FavouriteRepository"
    }
}
