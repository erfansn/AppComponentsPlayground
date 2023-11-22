package ir.erfansn.appcomponentsplayground.activity.intent

import android.app.Activity
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.component1
import androidx.activity.result.component2
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.activity.intent.ExplicitActivity.Companion.EXTRA_SAFE_INTENT
import ir.erfansn.appcomponentsplayground.activity.intent.ExplicitActivity.Companion.EXTRA_UNSAFE_INTENT
import ir.erfansn.appcomponentsplayground.databinding.ActivityIntentsBinding

class IntentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("erfansn.es@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Email subject")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.shareText.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Implicit Intent: Hello component!")
            }

            try {
                startActivity(Intent.createChooser(intent, "Share text"))
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Cannot found an activity to share text!", e)
                Toast.makeText(this, "There's no App!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.startForResult.setOnClickListener {
            val activityForResult =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { (resultCode, data) ->
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        Toast.makeText(
                            this,
                            data.getStringExtra(ExplicitActivity.EXTRA_MESSAGE),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            activityForResult.launch(Intent(this, ExplicitActivity::class.java))
        }
        binding.launchUnsafeIntent.setOnClickListener {
            val nestedIntent = Intent(this, ExplicitActivity::class.java).putExtra(ExplicitActivity.EXTRA_MESSAGE, "Unsafe Intent: Hello")

            Intent(this, ExplicitActivity::class.java).apply {
                putExtra(EXTRA_UNSAFE_INTENT, nestedIntent)
            }.also(::startActivity)
        }
        binding.launchSafeIntent.setOnClickListener {
            val nestedPendingIntent = PendingIntent.getActivity(
                this,
                0,
                Intent(this, ExplicitActivity::class.java).putExtra(ExplicitActivity.EXTRA_MESSAGE, "Safe Intent: Hello"),
                PendingIntent.FLAG_IMMUTABLE
            )

            Intent(this, ExplicitActivity::class.java).apply {
                putExtra(EXTRA_SAFE_INTENT, nestedPendingIntent)
            }.also(::startActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Activity was ${if (isFinishing) "Finished" else "Recreated"}")
    }

    companion object {
        private val TAG = IntentsActivity::class.simpleName
    }
}