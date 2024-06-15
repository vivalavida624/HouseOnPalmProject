package com.map08.houseonpalm.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.map08.houseonpalm.models.House



class HouseRepository {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val housesRef: DatabaseReference = database.getReference("houses")

    val houses = listOf(
        House("123 Main St", 3, 250000.0, true, "None", "https://example.com/house1.jpg"),
        House("456 Elm St", 4, 350000.0, false, "Leaky roof", "https://example.com/house2.jpg"),
        House("789 Oak St", 2, 180000.0, true, "Cracked foundation", "https://example.com/house3.jpg"),
        House("101 Pine Ave", 5, 500000.0, true, "None", "https://example.com/house4.jpg"),
        House("202 Maple Blvd", 3, 280000.0, false, "Flooded basement", "https://example.com/house5.jpg")
    )

    // 以下是CRUD操作
    // 添加新房屋
    fun addHouse(house: House): DatabaseReference {
        val key = housesRef.push().key ?: throw RuntimeException("Failed to push house to Firebase")
        val houseRef = housesRef.child(key)
        houseRef.setValue(house)
        return houseRef
    }

    // 更新房屋
    fun updateHouse(houseId: String, updatedHouse: House): DatabaseReference {
        val houseRef = housesRef.child(houseId)
        houseRef.setValue(updatedHouse)
        return houseRef
    }

    // 删除房屋
    fun deleteHouse(houseId: String): DatabaseReference {
        val houseRef = housesRef.child(houseId)
        houseRef.removeValue()
        return houseRef
    }

    // 获取所有房屋
    fun getAllHouses(): DatabaseReference {
        return housesRef
    }
}