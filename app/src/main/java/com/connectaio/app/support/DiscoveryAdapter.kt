package com.connectaio.app.support

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class DiscoveryAdapter() {
    private lateinit var btAdapter: BluetoothAdapter
    private lateinit var reciever: BroadcastReceiver
    private var devices: ArrayList<BluetoothDevice> =
        listOf<BluetoothDevice>() as ArrayList<BluetoothDevice>

    @SuppressLint("MissingPermission")
    fun init(context: Context){

        // Setup bluetooth manager
        val btManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter

        // TODO: Add check for successful initialization
        if (!btAdapter.isEnabled) { btAdapter.enable() }
        assert(btAdapter.isEnabled)

        // Build reciever object for device discovery
        reciever = object: BroadcastReceiver() {
            override fun onReceive(cont: Context?, intent: Intent?) {
                if (intent?.action == ACTION_FOUND) {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (!devices.contains(device) && device != null) {
                            devices.add(device)
                    }
                }
            }
        }

        // Cancel automatic discovery
        if (btAdapter.isDiscovering) { btAdapter.cancelDiscovery() }
    }

    @SuppressLint("MissingPermission")
    fun startDiscovery(context: Context) {
        // Register all device discoveries using IntentFilter class
        val intentFilter = IntentFilter().apply {
            addAction(ACTION_FOUND)
        }
        // Start filtering actions
        context.registerReceiver(reciever, intentFilter)
        // Start device discovery
        btAdapter.startDiscovery()
    }

    @SuppressLint("MissingPermission")
    fun terminateDiscovery(context: Context) {
        btAdapter.cancelDiscovery()
    }
}