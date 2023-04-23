package com.connectaio.app.support

import android.content.Intent

interface OnAudioCaptureResultListener {
    fun onAudioCaptureResult(resultCode: Int, data: Intent?)
}