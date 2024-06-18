package com.map08.houseonpalm.ui.broker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.map08.houseonpalm.R
import com.map08.houseonpalm.models.Broker

class BrokerAdapter(
    private val onEdit: (Broker) -> Unit,
    private val onDelete: (String) -> Unit
) : ListAdapter<Broker, BrokerAdapter.BrokerViewHolder>(BrokerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrokerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_broker, parent, false)
        return BrokerViewHolder(view, onEdit, onDelete)
    }

    override fun onBindViewHolder(holder: BrokerViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    class BrokerViewHolder(itemView: View, private val onEdit: (Broker) -> Unit, private val onDelete: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(broker: Broker) {
//            itemView.broker_name.text = broker.name
//            itemView.broker_email.text = broker.email
//            itemView.broker_phone.text = broker.phone
            itemView.findViewById<TextView>(R.id.broker_name).text = broker.name
            itemView.findViewById<TextView>(R.id.broker_email).text = broker.email
            itemView.findViewById<TextView>(R.id.broker_phone).text = broker.phone

//            itemView.edit_button.setOnClickListener { onEdit(broker) }
//            itemView.delete_button.setOnClickListener { onDelete(broker.id) }
//            itemView.findViewById<TextView>(R.id.edit_button).setOnClickListener { onEdit(broker) }
//            itemView.findViewById<TextView>(R.id.delete_button).setOnClickListener { onDelete(broker.id) }
        }
    }

    class BrokerDiffCallback : DiffUtil.ItemCallback<Broker>() {
        override fun areItemsTheSame(oldItem: Broker, newItem: Broker): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Broker, newItem: Broker): Boolean {
            return oldItem == newItem
        }
    }
}