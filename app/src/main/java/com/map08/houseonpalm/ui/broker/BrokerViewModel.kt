package com.map08.houseonpalm.ui.broker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.map08.houseonpalm.models.Broker
import com.map08.houseonpalm.repository.BrokerRepository

class BrokerViewModel : ViewModel() {

    private val brokerRepository = BrokerRepository()

    private val _brokers = MutableLiveData<List<Broker>>()
    val brokers: LiveData<List<Broker>> get() = _brokers

    fun addBroker(broker: Broker) {
        brokerRepository.addBroker(broker)
        fetchBrokers()
    }

    fun fetchBrokers() {
        brokerRepository.getBrokers { brokersList ->
            _brokers.value = brokersList
        }
    }

    fun updateBroker(broker: Broker) {
        brokerRepository.updateBroker(broker)
        fetchBrokers()
    }

    fun deleteBroker(brokerId: String) {
        brokerRepository.deleteBroker(brokerId)
        fetchBrokers()
    }
}