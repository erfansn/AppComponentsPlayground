package ir.erfansn.appcomponentsplayground

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ir.erfansn.appcomponentsplayground.activity.OwnActivity
import ir.erfansn.appcomponentsplayground.broadcast.BroadcastReceiverActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityMainBinding
import ir.erfansn.appcomponentsplayground.provider.ProviderActivity
import ir.erfansn.appcomponentsplayground.service.ServiceActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityComponent.setOnClickListener(this)
        binding.serviceComponent.setOnClickListener(this)
        binding.broadcastReceiverComponent.setOnClickListener(this)
        binding.contentProviderComponent.setOnClickListener(this)
    }

    override fun onClick(button: View) {
        val destinationActivity = when (button.id) {
            R.id.activity_component -> OwnActivity::class
            R.id.service_component -> ServiceActivity::class
            R.id.broadcast_receiver_component -> BroadcastReceiverActivity::class
            R.id.content_provider_component -> ProviderActivity::class
            else -> throw IllegalArgumentException()
        }

        startActivity(Intent(this, destinationActivity.java))
    }
}
