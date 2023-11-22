package ir.erfansn.appcomponentsplayground.activity.launchmode.singletask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivitySingleTaskSameAffinityBinding

open class SameAffinityActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleTaskSameAffinityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleTaskSameAffinityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.anotherActivity.setOnClickListener {
            startActivity(Intent(this, AnotherSameAffinityActivity::class.java))
        }
    }
}
