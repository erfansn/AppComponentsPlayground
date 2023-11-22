package ir.erfansn.appcomponentsplayground.activity.launchmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.document.DocumentActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.document.NeverDocumentActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.document.UniqueDocumentActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.singletask.NonSameAffinityActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.singletask.SameAffinityActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.standard.SimpleActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.singletop.SimpleActivity as SingleTopSimpleActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityLaunchModeBinding

class LaunchModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.standardSimple.setOnClickListener {
            startActivity(Intent(this, SimpleActivity::class.java))
        }

        binding.singleTopSimple.setOnClickListener {
            startActivity(Intent(this, SingleTopSimpleActivity::class.java))
        }

        binding.sameAffinity.setOnClickListener {
            // We could set FLAG_ACTIVITY_NEW_TASK instead of declare launchMode attribute inside
            // the Manifest file.
            startActivity(Intent(this, SameAffinityActivity::class.java))
        }
        binding.nonSameAffinity.setOnClickListener {
            startActivity(Intent(this, NonSameAffinityActivity::class.java))
        }

        binding.newDoc.setOnClickListener {
            // Equals with when set `intoExisting` to android:documentLaunchMode for activity element in Manifest
            // Setting value to android:autoRemoveFromRecents overrides the affect of FLAG_ACTIVITY_RETAIN_IN_RECENTS
            startActivity(
                Intent(this, DocumentActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS)
                }
            )
        }
        binding.uniqueDoc.setOnClickListener {
            // Equals with when set `always` to android:documentLaunchMode for activity element in Manifest
            // It only appears in 5 unique task in Recents Screen because of Manifest value set
            startActivity(
                Intent(this, UniqueDocumentActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                }
            )
        }
        binding.neverDoc.setOnClickListener {
            // The value set in Manifest has caused Intent flags to be overridden
            startActivity(
                Intent(this, NeverDocumentActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                }
            )
        }
    }
}
