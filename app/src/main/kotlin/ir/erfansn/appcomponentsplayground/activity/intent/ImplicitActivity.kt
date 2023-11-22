package ir.erfansn.appcomponentsplayground.activity.intent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityImplicitBinding

class ImplicitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImplicitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImplicitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recievedText.text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT) ?: return
    }
}
