package ir.erfansn.appcomponentsplayground.service.bound.remote

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast

class RemoteBoundService : Service() {

    private lateinit var messenger: Messenger

    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            if (msg.what == MSG_SAY_HELLO) {
                Toast.makeText(applicationContext, "RemoteBoundService: Hello", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        messenger = Messenger(IncomingHandler(this))
    }

    override fun onBind(intent: Intent): IBinder? {
        return messenger.binder
    }

    companion object {
        const val MSG_SAY_HELLO = 1
    }
}
