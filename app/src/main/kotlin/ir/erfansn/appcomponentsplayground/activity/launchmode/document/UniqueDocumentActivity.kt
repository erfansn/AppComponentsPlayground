package ir.erfansn.appcomponentsplayground.activity.launchmode.document

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityDocumentBinding
import ir.erfansn.appcomponentsplayground.databinding.ActivityUniqueDocumentBinding

class UniqueDocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUniqueDocumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUniqueDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
