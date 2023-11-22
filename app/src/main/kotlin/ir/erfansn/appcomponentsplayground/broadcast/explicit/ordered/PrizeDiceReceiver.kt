package ir.erfansn.appcomponentsplayground.broadcast.explicit.ordered

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class PrizeDiceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_DICE_ROLLING) {
            if (abortBroadcast) return

            val result = getResultExtras(false) ?: return
            val previousDiceNumber = result.getInt(KEY_DICE_RESULT)

            val diceNumber = randomDiceNumber
            Log.d("PrizeDiceReceiver", diceNumber.toString())

            Toast.makeText(
                context,
                "$diceNumber + $previousDiceNumber",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
