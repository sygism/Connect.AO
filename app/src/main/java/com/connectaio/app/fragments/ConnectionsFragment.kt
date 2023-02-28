package com.connectaio.app.fragments

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.connectaio.app.R
import com.connectaio.app.databinding.FragmentConnectionsFragmentBinding
import com.connectaio.app.support.BluetoothConnection
import com.google.android.material.slider.Slider


class ConnectionsFragment : Fragment() {

    private lateinit var binding: FragmentConnectionsFragmentBinding
    private var isLConnected: Boolean = false
    private var isRConnected: Boolean = false
    private var rSocket: BluetoothSocket? = null
    private var lSocket: BluetoothSocket? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentConnectionsFragmentBinding.inflate(layoutInflater)

        binding.connectBtnL.setOnClickListener {
            // Create a new discovery adapter
            val discoveryAdapter = BluetoothConnection()
            // Initialize the adapter
            this.context?.let{ it1 -> discoveryAdapter.init(it1) }
            // Start discovery
            this.context?.let { it1 -> discoveryAdapter.startDiscovery(it1) }


        }
        return binding.root
    }
}