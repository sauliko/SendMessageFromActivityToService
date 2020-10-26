package com.saulcemal.sendmessagefromactivitytoservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.ActivityManager
import android.content.Intent
import android.view.View
import android.widget.Toast
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity() {
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serviceIntent = Intent(applicationContext, MessageService::class.java)
    }

    fun sendLogin(view: View){
        startService(serviceIntent)
        if (isMyServiceRunning(MessageService::class.java)){
            Toast.makeText(applicationContext, "Service Started", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendTest(view: View){
        //Post Events
        EventBus.getDefault().post(MessageEvent("TEST"))
    }

    fun sendLogout(view: View){
        stopService(serviceIntent)
        if (!isMyServiceRunning(MessageService::class.java)){
            Toast.makeText(applicationContext, "Service Stopped", Toast.LENGTH_SHORT).show()
        }

    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}