package ir.erfansn.appcomponentsplayground.activity.launchmode.singletask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivitySingleTaskNonSameAffinityBinding

class NonSameAffinityActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleTaskNonSameAffinityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleTaskNonSameAffinityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rerunActivity.setOnClickListener {
            startActivity(Intent(this, this::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            Toast.makeText(this, "Hello again", Toast.LENGTH_SHORT).show()
        }
    }
}