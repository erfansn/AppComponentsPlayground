package ir.erfansn.appcomponentsplayground.broadcast.implicit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log

class DynamicImplicitReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            // Cause ANR dialog
            Thread.sleep(32000)
            Log.i("DynamicImplicitReceiver", "You can see me")
        }
    }
}
