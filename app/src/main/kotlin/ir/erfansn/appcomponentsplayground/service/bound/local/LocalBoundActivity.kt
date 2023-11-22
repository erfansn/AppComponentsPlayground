package ir.erfansn.appcomponentsplayground.service.bound.local

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityLocalBoundBinding

class LocalBoundActivity : AppCompatActivity() {

    private var isBounded: Boolean = false
    private lateinit var localBoundService: LocalBoundService

    private lateinit var binding: ActivityLocalBoundBinding

    private val localBoundConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            service as LocalBoundService.LocalBinder
            localBoundService = service.getService()
            isBounded = true
            Toast.makeText(this@LocalBoundActivity, "Local bound service connected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Toast.makeText(this@LocalBoundActivity, "Local bound service disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalBoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getRandomNumber.setOnClickListener {
            if (isBounded) {
                binding.number.text = localBoundService.randomNumber.toString()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, LocalBoundService::class.java),
            localBoundConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        if (isBounded) {
            unbindService(localBoundConnection)
            isBounded = false
        }
    }
}