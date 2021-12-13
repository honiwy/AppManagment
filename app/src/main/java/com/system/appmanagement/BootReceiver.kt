package com.system.appmanagement

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.system.appmanagement.service.BlockService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action || Intent.ACTION_MY_PACKAGE_REPLACED == intent.action) {
            val startServiceIntent = Intent(context, BlockService::class.java)
            context.startForegroundService(startServiceIntent)
        }
    }
}