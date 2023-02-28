package com.connectaio.app

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.connectaio.app.support.BluetoothConnection
import java.util.*
import kotlin.collections.ArrayList

class DevicesListViewAdapter(
    private val context: Activity, private val contents: ArrayList<BluetoothDevice>,
    private val bluetoothConnection: BluetoothConnection): BaseAdapter() {

    override fun getCount(): Int {
        return contents.size
    }

    override fun getItem(p0: Int): Any {
        return contents[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder", "MissingPermission")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.discovered_device_list_layout,
            null, true)
        val deviceName = contents[position].name
        val deviceUUID = contents[position].uuids

        // Display device name
        rowView.findViewById<TextView>(R.id.device_name_item).text = deviceName.toString()
        rowView.findViewById<TextView>(R.id.device_uuid_item).text = deviceUUID.toString()

        rowView.setOnClickListener {

            // Create a thread, that attempts connecting to the selected device
            try {
                bluetoothConnection.ConnectThread(contents[position], UUID.fromString(
                    deviceUUID.toString()
                ))
            } catch (e: Exception) {
                Toast.makeText(context, "Error connecting to $deviceName",Toast.LENGTH_SHORT).show()
            }

        }
        return rowView
    }
}