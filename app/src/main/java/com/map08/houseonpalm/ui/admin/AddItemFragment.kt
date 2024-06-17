package com.map08.houseonpalm.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.map08.houseonpalm.R
import com.map08.houseonpalm.ui.broker.BrokerFragment
import com.map08.houseonpalm.ui.house.HouseFragment

class AddItemsFragment : Fragment() {

    private lateinit var houseFragment: HouseFragment
    private lateinit var brokerFragment: BrokerFragment
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        houseFragment = HouseFragment()
        brokerFragment = BrokerFragment()
        auth = FirebaseAuth.getInstance()

        childFragmentManager.beginTransaction()
            .add(houseFragment, "HouseFragment")
            .add(brokerFragment, "BrokerFragment")
            .commitNow()

        // 在这里初始化视图和设置按钮点击事件等
        val addHouseButton: Button = view.findViewById(R.id.addHouseButton)
        val addBrokerButton: Button = view.findViewById(R.id.addBrokerButton)
        val logOutButton: Button = view.findViewById(R.id.logOutButton)

        addHouseButton.setOnClickListener {
            houseFragment.showAddHouseDialog()
        }

        addBrokerButton.setOnClickListener {
            brokerFragment.showAddBrokerDialog()
        }

        logOutButton.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_AddItemsFragment_to_SignInFragment)
        }


    }
}