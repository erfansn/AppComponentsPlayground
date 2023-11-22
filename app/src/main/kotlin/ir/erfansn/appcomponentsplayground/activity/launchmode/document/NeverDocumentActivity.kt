package ir.erfansn.appcomponentsplayground.activity.launchmode.document

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityNeverDocumentBinding

class NeverDocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNeverDocumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNeverDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
