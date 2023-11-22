package ir.erfansn.appcomponentsplayground.service

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ir.erfansn.appcomponentsplayground.databinding.ActivityServiceBinding
import ir.erfansn.appcomponentsplayground.service.bound.local.LocalBoundActivity
import ir.erfansn.appcomponentsplayground.service.bound.remote.RemoteBoundActivity
import ir.erfansn.appcomponentsplayground.service.started.StartedService

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startJob.setOnClickListener {
            val intent = Intent(this, StartedService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }
        binding.stopJob.setOnClickListener {
            stopService(Intent(this, StartedService::class.java))
        }

        binding.localBinder.setOnClickListener {
            startActivity(Intent(this, LocalBoundActivity::class.java))
        }
        binding.remoteBinder.setOnClickListener {
            startActivity(Intent(this, RemoteBoundActivity::class.java))
        }
    }
}