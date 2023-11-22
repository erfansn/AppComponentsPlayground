package ir.erfansn.appcomponentsplayground.broadcast.implicit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StaticImplicitReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val broadcast = buildString {
                append("Action: ${intent.action}\n")
                append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}")
            }
            Log.i("StaticImplicitReceiver", broadcast)
        }
    }
}