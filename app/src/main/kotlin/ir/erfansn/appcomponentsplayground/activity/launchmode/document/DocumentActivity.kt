package ir.erfansn.appcomponentsplayground.activity.launchmode.document

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityDocumentBinding

class DocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.finishTask.setOnClickListener {
            // If activity starter Intent contains flag `FLAG_ACTIVITY_RETAIN_IN_RECENTS`
            // with back or finish() its tasks remains in Recents Screen
            finishAndRemoveTask()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Toast.makeText(this, "Reusing activity", Toast.LENGTH_SHORT).show()
    }
}
