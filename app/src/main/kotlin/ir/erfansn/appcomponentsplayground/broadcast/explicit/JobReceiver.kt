package ir.erfansn.appcomponentsplayground.broadcast.explicit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

class JobReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_SHORT_JOB) scope.goAsync {
            delay(2.seconds)
            withContext(Dispatchers.Main.immediate) {
                Toast.makeText(context, intent.getStringExtra(EXTRA_COMPLETE_MESSAGE), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val ACTION_SHORT_JOB = "ir.erfansn.appcomponentsplayground.broadcast.action.JOB"
        const val EXTRA_COMPLETE_MESSAGE = "ir.erfansn.appcomponentsplayground.broadcast.extra.COMPLETE_MESSAGE"
    }
}

context(BroadcastReceiver)
private inline fun CoroutineScope.goAsync(crossinline block: suspend BroadcastReceiver.PendingResult.() -> Unit) {
    val pendingResult = this@BroadcastReceiver.goAsync()
    launch {
        try {
            pendingResult.block()
        } finally {
            pendingResult.finish()
        }
    }
}
