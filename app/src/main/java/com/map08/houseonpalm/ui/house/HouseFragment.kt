package com.map08.houseonpalm.ui.house

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.map08.houseonpalm.R
import com.map08.houseonpalm.models.House

class HouseFragment : Fragment() {

    private val houseViewModel: HouseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_house, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.house_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = HouseAdapter(
            onHouseClicked = { house -> showEditDialog(house) }
        )
        recyclerView.adapter = adapter

        houseViewModel.houses.observe(viewLifecycleOwner, Observer { houses ->
            houses?.let {
                adapter.submitList(it)
            }
        })

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            // 添加一个House的逻辑
            showAddDialog()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        houseViewModel.fetchHouses()
    }

    fun showAddDialog() {
        // 实现添加对话框
        // 在对话框中收集用户输入，并调用 houseViewModel.addHouse(newHouse)
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_house, null)
        val addressEditText = dialogLayout.findViewById<EditText>(R.id.address_edit_text)
        val roomsEditText = dialogLayout.findViewById<EditText>(R.id.rooms_edit_text)
        val priceEditText = dialogLayout.findViewById<EditText>(R.id.price_edit_text)
        val imageUrlEditText = dialogLayout.findViewById<EditText>(R.id.image_url_edit_text)

        with(builder) {
            setTitle("Add House")
            setPositiveButton("OK") { dialog, which ->
                val address = addressEditText.text.toString()
                val numberOfRooms = roomsEditText.text.toString().toIntOrNull() ?: 0
                val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
                val imageUrl = imageUrlEditText.text.toString()
                val newHouse = House(address = address, numberOfRooms = numberOfRooms, price = price, imageUrl = imageUrl)
                houseViewModel.addHouse(newHouse)
            }
            setNegativeButton("Cancel") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun showEditDialog(house: House) {
        // 实现编辑对话框
        // 在对话框中收集用户输入，并调用 houseViewModel.updateHouse(updatedHouse)
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_house, null)
        val addressEditText = dialogLayout.findViewById<EditText>(R.id.address_edit_text)
        val roomsEditText = dialogLayout.findViewById<EditText>(R.id.rooms_edit_text)
        val priceEditText = dialogLayout.findViewById<EditText>(R.id.price_edit_text)
        val imageUrlEditText = dialogLayout.findViewById<EditText>(R.id.image_url_edit_text)

        addressEditText.setText(house.address)
        roomsEditText.setText(house.numberOfRooms.toString())
        priceEditText.setText(house.price.toString())
        imageUrlEditText.setText(house.imageUrl)

        with(builder) {
            setTitle("Edit House")
            setPositiveButton("OK") { dialog, which ->
                val address = addressEditText.text.toString()
                val numberOfRooms = roomsEditText.text.toString().toIntOrNull() ?: 0
                val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
                val imageUrl = imageUrlEditText.text.toString()
                val updatedHouse = house.copy(address = address, numberOfRooms = numberOfRooms, price = price, imageUrl = imageUrl)
                houseViewModel.updateHouse(updatedHouse)
            }
            setNegativeButton("Cancel") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }
}