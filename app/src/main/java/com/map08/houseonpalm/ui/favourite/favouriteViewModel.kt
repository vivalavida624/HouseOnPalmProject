package com.map08.houseonpalm.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import com.map08.houseonpalm.models.Favourite
import com.map08.houseonpalm.repository.FavouriteRepository

class FavouriteViewModel : ViewModel() {

    private val favouriteRepository = FavouriteRepository()
    private val _favourites = MutableLiveData<List<Favourite>>()
    val favourites: LiveData<List<Favourite>> get() = _favourites

    private var favouritesListenerRegistration: ListenerRegistration? = null

    init {
        fetchFavourites()
    }

    fun fetchFavourites() {
        favouriteRepository.getFavourites { favouritesList ->
            _favourites.postValue(favouritesList)
        }
    }

    fun addFavourite(favourite: Favourite) {
        favouriteRepository.addFavourite(favourite)
        fetchFavourites() // Refresh the list after adding
    }

    fun updateFavourite(favourite: Favourite) {
        favouriteRepository.updateFavourite(favourite)
        fetchFavourites() // Refresh the list after updating
    }

    fun deleteFavourite(favouriteId: String) {
        favouriteRepository.deleteFavourite(favouriteId)
        fetchFavourites() // Refresh the list after deleting
    }

    override fun onCleared() {
        super.onCleared()
        favouritesListenerRegistration?.remove()
    }
}
