package ir.erfansn.appcomponentsplayground.service.bound.remote

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityRemoteBoundBinding

class RemoteBoundActivity : AppCompatActivity() {

    private var isBounded: Boolean = false
    private lateinit var messengerBoundService: Messenger

    private lateinit var binding: ActivityRemoteBoundBinding

    private val remoteBoundConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            messengerBoundService = Messenger(service)
            isBounded = true
            Toast.makeText(this@RemoteBoundActivity, "Remote bound service connected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Toast.makeText(this@RemoteBoundActivity, "Remote bound service disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemoteBoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sayHello.setOnClickListener {
            if (isBounded) {
                val message = Message.obtain(null, RemoteBoundService.MSG_SAY_HELLO, null)
                try {
                    messengerBoundService.send(message)
                } catch (e: RemoteException) {
                    Log.e("RemoteBoundActivity", e.message, e)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            Intent(this, RemoteBoundService::class.java),
            remoteBoundConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        if (isBounded) {
            unbindService(remoteBoundConnection)
            isBounded = false
        }
    }
}