package ir.erfansn.appcomponentsplayground.service.bound.local

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import kotlin.random.Random

class LocalBoundService : Service() {

    private val binder = LocalBinder()

    val randomNumber get() = Random.nextInt()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "All clients unbind the service", Toast.LENGTH_SHORT).show()
        return super.onUnbind(intent)
    }

    inner class LocalBinder : Binder() {
        fun getService() = this@LocalBoundService
    }
}
