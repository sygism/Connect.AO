package com.connectaio.app.support

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.util.*

@SuppressLint("MissingPermission")
private class ConnectThread(device: BluetoothDevice, uuid: UUID): Thread() {
    private val bSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        device.createRfcommSocketToServiceRecord(uuid)
    }

    override fun run() {

    }
}