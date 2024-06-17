package com.map08.houseonpalm.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.map08.houseonpalm.R
import com.map08.houseonpalm.ui.broker.BrokerFragment
import com.map08.houseonpalm.ui.house.HouseFragment

class AddItemsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 在这里初始化视图和设置按钮点击事件等
        val addHouseButton: Button = view.findViewById(R.id.addHouseButton)
        val addBrokerButton: Button = view.findViewById(R.id.addBrokerButton)

//        addHouseButton.setOnClickListener {
//             //显示添加房屋的对话框或导航到添加房屋的页面
//            val houseFragment = HouseFragment()
//            houseFragment.showAddDialog()
//        }

//        addBrokerButton.setOnClickListener {
//            // 显示添加经纪人的对话框或导航到添加经纪人的页面
//            val brokerFragment = BrokerFragment()
//            brokerFragment.showAddDialog()
//        }

        addHouseButton.setOnClickListener {
            findNavController().navigate(R.id.action_AddItemsFragment_to_HouseFragment)
        }

        addBrokerButton.setOnClickListener {
            findNavController().navigate(R.id.action_AddItemsFragment_to_BrokerFragment)
        }
    }
}