package ir.erfansn.appcomponentsplayground.service.started

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.*
import android.widget.Toast
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import ir.erfansn.appcomponentsplayground.service.ServiceActivity

// This service implemented like [IntentService] thus it's not suitable for android 8 and later
class StartedService : Service() {

    private lateinit var handlerThread: HandlerThread
    private val notificationManager by lazy { NotificationManagerCompat.from(this) }

    private lateinit var serviceHandler: ServiceHandler

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            try {
                Thread.sleep(4000)
                Toast.makeText(this@StartedService, "Job Completed", Toast.LENGTH_SHORT).show()
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            } finally {
                stopSelf(msg.arg1)
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
        }
    }

    override fun onCreate() {
        createNotificationChannel()
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        handlerThread = HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            // Get the HandlerThread's Looper and use it for our Handler
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        makeServiceForeground()
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        serviceHandler.obtainMessage().also { msg ->
            msg.arg1 = startId

            serviceHandler.sendMessage(msg)
        }
        // If we get killed, after returning from here, restart
        return START_REDELIVER_INTENT
    }

    private fun makeServiceForeground() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setContentTitle("Started service")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(PendingIntent.getActivity(this, 0, Intent(this, ServiceActivity::class.java), PendingIntent.FLAG_IMMUTABLE))
            .build()
        ServiceCompat.startForeground(this, STARTED_SERVICE_NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE)
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannelCompat.Builder(NOTIFICATION_CHANNEL, NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName("Started service")
            .build()
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        handlerThread.interrupt()
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val STARTED_SERVICE_NOTIFICATION_ID = 1

        private const val NOTIFICATION_CHANNEL = "started_service_channel"
    }
}