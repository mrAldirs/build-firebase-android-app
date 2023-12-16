package com.example.silapor_v2.utils.custom

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class FCMTokenSender(private val context: Context) {

    fun sendFCMTokenToServer(token: String) {
        val url = "http://192.168.137.1/api_fcm/notification_json.php"

        val jsonObject = JSONObject().apply {
            put("fcm_token", token)
        }

        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            { response ->
                Log.d("FCMTokenSender", "Response: $response")
            },
            { error ->
                Log.e("FCMTokenSender", "Error: $error")
            })

        Volley.newRequestQueue(context).add(request)
    }
}
