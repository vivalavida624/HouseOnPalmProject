package com.map08.houseonpalm.ui.favourite

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
import com.map08.houseonpalm.models.Favourite

class FavouriteFragment : Fragment() {

    private val favouriteViewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.favourite_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = FavouriteAdapter(
            onFavouriteClicked = { favourite -> showEditDialog(favourite) }
        )
        recyclerView.adapter = adapter

        favouriteViewModel.favourites.observe(viewLifecycleOwner, Observer { favourites ->
            favourites?.let {
                adapter.submitList(it)
            }
        })

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            // 添加一个Favourite的逻辑
            showAddFavouriteDialog()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        favouriteViewModel.fetchFavourites()
    }

    fun showAddFavouriteDialog() {
        // 实现添加对话框
        // 在对话框中收集用户输入，并调用 favouriteViewModel.addFavourite(newFavourite)
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_favourite, null)
        val addressEditText = dialogLayout.findViewById<EditText>(R.id.address_edit_text)
        val roomsEditText = dialogLayout.findViewById<EditText>(R.id.rooms_edit_text)
        val priceEditText = dialogLayout.findViewById<EditText>(R.id.price_edit_text)
        val imageUrlEditText = dialogLayout.findViewById<EditText>(R.id.image_url_edit_text)

        with(builder) {
            setTitle("Add Favourite")
            setPositiveButton("OK") { dialog, which ->
                val address = addressEditText.text.toString()
                val numberOfRooms = roomsEditText.text.toString().toIntOrNull() ?: 0
                val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
                val imageUrl = imageUrlEditText.text.toString()
                val newFavourite = Favourite(address = address, numberOfRooms = numberOfRooms, price = price, imageUrl = imageUrl)
                favouriteViewModel.addFavourite(newFavourite)
            }
            setNegativeButton("Cancel") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun showEditDialog(favourite: Favourite) {
        // 实现编辑对话框
        // 在对话框中收集用户输入，并调用 favouriteViewModel.updateFavourite(updatedFavourite)
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_favourite, null)
        val addressEditText = dialogLayout.findViewById<EditText>(R.id.address_edit_text)
        val roomsEditText = dialogLayout.findViewById<EditText>(R.id.rooms_edit_text)
        val priceEditText = dialogLayout.findViewById<EditText>(R.id.price_edit_text)
        val imageUrlEditText = dialogLayout.findViewById<EditText>(R.id.image_url_edit_text)

        addressEditText.setText(favourite.address)
        roomsEditText.setText(favourite.numberOfRooms.toString())
        priceEditText.setText(favourite.price.toString())
        imageUrlEditText.setText(favourite.imageUrl)

        with(builder) {
            setTitle("Edit Favourite")
            setPositiveButton("OK") { dialog, which ->
                val address = addressEditText.text.toString()
                val numberOfRooms = roomsEditText.text.toString().toIntOrNull() ?: 0
                val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0
                val imageUrl = imageUrlEditText.text.toString()
                val updatedFavourite = favourite.copy(address = address, numberOfRooms = numberOfRooms, price = price, imageUrl = imageUrl)
                favouriteViewModel.updateFavourite(updatedFavourite)
            }
            setNegativeButton("Cancel") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }
}
