package ir.erfansn.appcomponentsplayground.activity.launchmode.singletop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivitySingleTopSimpleBinding

class SimpleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleTopSimpleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleTopSimpleBinding.inflate(layoutInflater)
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
