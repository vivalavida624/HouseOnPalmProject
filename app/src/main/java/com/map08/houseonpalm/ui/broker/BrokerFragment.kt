package com.map08.houseonpalm.ui.broker

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
import com.map08.houseonpalm.models.Broker

class BrokerFragment : Fragment() {

    private val brokerViewModel: BrokerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_broker, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.broker_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = BrokerAdapter(
            onEdit = { broker -> showEditDialog(broker) },
            onDelete = { brokerId -> brokerViewModel.deleteBroker(brokerId) }
        )
        recyclerView.adapter = adapter

        brokerViewModel.brokers.observe(viewLifecycleOwner, Observer { brokers ->
            brokers?.let {
                adapter.submitList(it)
            }
        })

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            // 添加一个Broker的逻辑
            showAddDialog()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        brokerViewModel.fetchBrokers()
    }

    private fun showAddDialog() {
        // 实现添加对话框
        // 在对话框中收集用户输入，并调用 brokerViewModel.addBroker(newBroker)
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_broker, null)
        val nameEditText = dialogLayout.findViewById<EditText>(R.id.name_edit_text)
        val emailEditText = dialogLayout.findViewById<EditText>(R.id.email_edit_text)
        val phoneEditText = dialogLayout.findViewById<EditText>(R.id.phone_edit_text)

        with(builder) {
            setTitle("Add Broker")
            setPositiveButton("OK") { dialog, which ->
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val phone = phoneEditText.text.toString()
                val newBroker = Broker(name = name, email = email, phone = phone)
                brokerViewModel.addBroker(newBroker)
            }
            setNegativeButton("Cancel") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun showEditDialog(broker: Broker) {
        // 实现编辑对话框
        // 在对话框中收集用户输入，并调用 brokerViewModel.updateBroker(updatedBroker)
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_broker, null)
        val nameEditText = dialogLayout.findViewById<EditText>(R.id.name_edit_text)
        val emailEditText = dialogLayout.findViewById<EditText>(R.id.email_edit_text)
        val phoneEditText = dialogLayout.findViewById<EditText>(R.id.phone_edit_text)

        nameEditText.setText(broker.name)
        emailEditText.setText(broker.email)
        phoneEditText.setText(broker.phone)

        with(builder) {
            setTitle("Edit Broker")
            setPositiveButton("OK") { dialog, which ->
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val phone = phoneEditText.text.toString()
                val updatedBroker = broker.copy(name = name, email = email, phone = phone)
                brokerViewModel.updateBroker(updatedBroker)
            }
            setNegativeButton("Cancel") { dialog, which -> }
            setView(dialogLayout)
            show()
        }
    }
}