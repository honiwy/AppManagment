package com.system.appmanagement.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.system.appmanagement.utils.SharedPreference
import com.system.appmanagement.utils.Util

class BlockService : Service() {
    // This is the object that receives interactions from clients.
    private lateinit var scanningThread: Thread

    override fun onCreate() {
        super.onCreate()

        scanningThread = lockThread()
        scanningThread.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.i("LockService", "onBind")
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        Log.i("LockService", "Service Destroy")
        super.onDestroy()
    }

    private fun lockThread() = Thread {
        while (true) {
            while (SharedPreference.blockStatus == true) {
                val runningPackageName = Util.getForegroundApp(this)
                Log.i("LockService", "onStartCommand: $runningPackageName")
                if (runningPackageName == "com.google.android.GoogleCamera") {
                    Looper.prepare();
                    Toast.makeText(applicationContext, "Oops", Toast.LENGTH_SHORT).show()
                    Looper.loop();
                    //                    val intentA = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    //                    startActivity(intentA)
                    //                    val uri = Uri.parse("tel:10010")
                    //                    val intentD = Intent(Intent.ACTION_DIAL, uri)
                    //                    intentD.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    //                    startActivity(intentD)

                    //                    val homeIntent = Intent(this, MainActivity::class.java)
                    //                    homeIntent.action = Intent.ACTION_MAIN
                    //                    homeIntent.flags =
                    //                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    //                    homeIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                    //                    startActivity(homeIntent)
                    //                    Log.i("LockService", "onStartCommand: home")


                    //                    //kill the blacklisted task which is now in the background
                    //                    val activityManager =
                    //                        this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    //                    activityManager.killBackgroundProcesses(runningPackageName)
                    //                    Log.i("LockService", "onStartCommand: kill $runningPackageName")
                    //
//                    val intent = Intent(this@BlockService, MainActivity::class.java)
//                    intent.flags =
//                        (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                                or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//                    startActivity(intent)
//                    Log.i("LockService", "onStartCommand: back")
                }
                Thread.sleep(3000)
            }
            Log.i("LockService", "keep checking")
            Thread.sleep(10000)
        }
    }


}