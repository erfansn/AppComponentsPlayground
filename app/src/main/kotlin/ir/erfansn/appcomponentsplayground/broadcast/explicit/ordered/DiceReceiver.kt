package ir.erfansn.appcomponentsplayground.broadcast.explicit.ordered

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf

class DiceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_DICE_ROLLING) {
            val diceNumber = randomDiceNumber
            Log.d("DiceReceiver", diceNumber.toString())

            if (diceNumber != 6) {
                Toast.makeText(context, diceNumber.toString(), Toast.LENGTH_SHORT).show()
                abortBroadcast()
                return
            }
            setResultExtras(bundleOf(KEY_DICE_RESULT to diceNumber))
        }
    }
}
