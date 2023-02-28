package com.connectaio.app.support

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class BluetoothConnection() {
    private lateinit var btAdapter: BluetoothAdapter
    private lateinit var reciever: BroadcastReceiver
    private var devices: ArrayList<BluetoothDevice> =
        listOf<BluetoothDevice>() as ArrayList<BluetoothDevice>
    private lateinit var connection: BluetoothSocket


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

    fun disconnect() {
        connection.close()
    }

    @SuppressLint("MissingPermission")
    inner class ConnectThread(device: BluetoothDevice, uuid: UUID): Thread() {
        private val bSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(uuid)
        }

        override fun run() {
            btAdapter.cancelDiscovery()

            bSocket?.let { socket ->
                // Attempts a connection to the speaker until either success or failure
                    socket.connect()
                    // Do stuff with the speaker
                    connection = socket
                }
            }

        fun cancel() {
            try {
                bSocket?.close()
            } catch (e: IOException) {
                Log.e("Error", "Error closing client socket")
            }
        }
    }
}