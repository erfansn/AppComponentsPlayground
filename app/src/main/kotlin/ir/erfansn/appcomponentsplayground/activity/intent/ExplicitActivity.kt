package ir.erfansn.appcomponentsplayground.activity.intent

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import ir.erfansn.appcomponentsplayground.databinding.ActivityExplicitBinding

class ExplicitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExplicitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExplicitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.launchNestedIntent.setOnClickListener {
            runCatching {
                IntentCompat.getParcelableExtra(
                    intent,
                    EXTRA_UNSAFE_INTENT,
                    Intent::class.java
                )
            }.recoverCatching {
                IntentCompat.getParcelableExtra(
                    intent,
                    EXTRA_SAFE_INTENT,
                    PendingIntent::class.java
                )
            }.onSuccess {
                when (it) {
                    is Intent -> startActivity(it)
                    is PendingIntent -> it.send()
                }
            }
        }
        binding.returnResult.setOnClickListener {
            setResult(
                Activity.RESULT_OK,
                Intent().putExtra(EXTRA_MESSAGE, "Intent: Hello master!")
            )
            finish()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            Toast.makeText(this, it.getStringExtra(EXTRA_MESSAGE), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_UNSAFE_INTENT = "ir.erfansn.appcomponentsplayground.activity.extra.UNSAFE_INTENT"
        const val EXTRA_SAFE_INTENT = "ir.erfansn.appcomponentsplayground.activity.extra.SAFE_INTENT"
        const val EXTRA_MESSAGE = "ir.erfansn.appcomponentsplayground.activity.extra.MESSAGE"
    }
}
