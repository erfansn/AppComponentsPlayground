package ir.erfansn.appcomponentsplayground.activity.launchmode.singletask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivitySingleTaskAnotherSameAffinityBinding

class AnotherSameAffinityActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleTaskAnotherSameAffinityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleTaskAnotherSameAffinityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.previousActivity.setOnClickListener {
            startActivity(Intent(this, SameAffinityActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "AnotherAffinity: I'm Destroyed", Toast.LENGTH_SHORT).show()
    }
}
