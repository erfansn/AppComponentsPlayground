package ir.erfansn.appcomponentsplayground.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.erfansn.appcomponentsplayground.broadcast.explicit.JobReceiver
import ir.erfansn.appcomponentsplayground.broadcast.explicit.ordered.ACTION_DICE_ROLLING
import ir.erfansn.appcomponentsplayground.broadcast.explicit.ordered.DiceReceiver
import ir.erfansn.appcomponentsplayground.broadcast.explicit.ordered.PrizeDiceReceiver
import ir.erfansn.appcomponentsplayground.broadcast.implicit.DynamicImplicitReceiver
import ir.erfansn.appcomponentsplayground.databinding.ActivityBroadcastReceiverBinding

class BroadcastReceiverActivity : AppCompatActivity() {

    private val dynamicImplicitReceiver = DynamicImplicitReceiver()

    private val jobReceiver = JobReceiver()
    private val diceReceiver = DiceReceiver()
    private val prizeDiceReceiver = PrizeDiceReceiver()

    private lateinit var binding: ActivityBroadcastReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doShortJob.setOnClickListener {
            Intent(JobReceiver.ACTION_SHORT_JOB)
                .putExtra(JobReceiver.EXTRA_COMPLETE_MESSAGE, "Short job completed!")
                .also(::sendBroadcast)
        }

        binding.rollTheDice.setOnClickListener {
            sendOrderedBroadcast(Intent(ACTION_DICE_ROLLING), null)
        }
    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            this,
            dynamicImplicitReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
            ContextCompat.RECEIVER_EXPORTED,
        )

        ContextCompat.registerReceiver(
            this,
            jobReceiver,
            IntentFilter(JobReceiver.ACTION_SHORT_JOB),
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )
        ContextCompat.registerReceiver(
            this,
            diceReceiver,
            IntentFilter(ACTION_DICE_ROLLING).apply { priority = 2 },
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )
        ContextCompat.registerReceiver(
            this,
            prizeDiceReceiver,
            IntentFilter(ACTION_DICE_ROLLING).apply { priority = 1 },
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(dynamicImplicitReceiver)

        unregisterReceiver(jobReceiver)
        unregisterReceiver(diceReceiver)
        unregisterReceiver(prizeDiceReceiver)
    }
}
