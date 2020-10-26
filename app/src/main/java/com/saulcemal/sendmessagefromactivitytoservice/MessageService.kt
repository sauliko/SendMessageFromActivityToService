package com.saulcemal.sendmessagefromactivitytoservice

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MessageService : Service() {

    private val TAG: String? = "InService"

    private inner class MessageRequestHandler() : Handler()   {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {
        Log.i(TAG, "onMessageEvent: " + event?.someString)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)

        //subscribe to EventBus
        EventBus.getDefault().register(this);
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private val messageMessenger: Messenger = Messenger(MessageRequestHandler())
    override fun onBind(intent: Intent?): IBinder {
        return messageMessenger.binder
    }

    override fun onDestroy() {
        super.onDestroy()

        //Unsubscribe to EventBus
        EventBus.getDefault().unregister(this);
    }
}