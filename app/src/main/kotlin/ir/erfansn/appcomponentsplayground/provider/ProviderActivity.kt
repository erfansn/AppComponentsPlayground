package ir.erfansn.appcomponentsplayground.provider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ir.erfansn.appcomponentsplayground.databinding.ActivityProviderBinding
import ir.erfansn.appcomponentsplayground.provider.content.VocabularyProviderActivity

class ProviderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val openDocumentLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { imageUri ->
            imageUri?.let {
                binding.image.isVisible = true
                binding.image.setImageURI(it)

                Log.i("ProviderActivity", imageUri.toString())
            }
        }
        binding.openImage.setOnClickListener {
            openDocumentLauncher.launch(arrayOf("image/*"))
        }

        binding.openVocabulary.setOnClickListener {
            startActivity(Intent(this, VocabularyProviderActivity::class.java))
        }
    }
}
