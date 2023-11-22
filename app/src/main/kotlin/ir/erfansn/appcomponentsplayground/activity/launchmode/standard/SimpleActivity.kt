package ir.erfansn.appcomponentsplayground.activity.launchmode.standard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityStandardSimpleBinding

class SimpleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStandardSimpleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStandardSimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rerunActivity.setOnClickListener {
            startActivity(Intent(this, this::class.java))
        }
    }
}
