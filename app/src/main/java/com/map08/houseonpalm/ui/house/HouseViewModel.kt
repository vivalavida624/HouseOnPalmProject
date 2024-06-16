package com.map08.houseonpalm.ui.house

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import com.map08.houseonpalm.models.House
import com.map08.houseonpalm.repository.HouseRepository

class HouseViewModel : ViewModel() {

    private val houseRepository = HouseRepository()
    private val _houses = MutableLiveData<List<House>>()
    val houses: LiveData<List<House>> get() = _houses

    private var housesListenerRegistration: ListenerRegistration? = null

    init {
        fetchHouses()
    }

    fun fetchHouses() {
        houseRepository.getHouses { housesList ->
            _houses.postValue(housesList)
        }
    }

    fun addHouse(house: House) {
        houseRepository.addHouse(house)
        fetchHouses() // Refresh the list after adding
    }

    fun updateHouse(house: House) {
        houseRepository.updateHouse(house)
        fetchHouses() // Refresh the list after updating
    }

    fun deleteHouse(houseId: String) {
        houseRepository.deleteHouse(houseId)
        fetchHouses() // Refresh the list after deleting
    }

    override fun onCleared() {
        super.onCleared()
        housesListenerRegistration?.remove()
    }
}