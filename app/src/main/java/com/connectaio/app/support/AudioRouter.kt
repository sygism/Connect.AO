package com.connectaio.app.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.projection.MediaProjectionManager

class AudioRouter: Activity(), OnAudioCaptureResultListener {

    private var mediaProjectionManager: MediaProjectionManager? = null
    private val CAPTURE_AUDIO: Int = 1123
    private var audioRecord: AudioRecord? = null
    private var audioTrack: AudioTrack? = null

    fun init(context: Context, activity: Activity) {
        // Initialize the MediaProjectionManager
        mediaProjectionManager = context.getSystemService(Context.MEDIA_PROJECTION_SERVICE)
                as MediaProjectionManager

        // Request user's permission to capture audio from the device
        val permissionIntent = mediaProjectionManager!!.createScreenCaptureIntent()
        activity.startActivityForResult(permissionIntent, CAPTURE_AUDIO)
    }

    fun OnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onAudioCaptureResult(resultCode: Int, data: Intent?) {
        TODO("Not yet implemented")
    }
}