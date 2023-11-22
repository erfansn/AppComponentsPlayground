package ir.erfansn.appcomponentsplayground.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ir.erfansn.appcomponentsplayground.R
import ir.erfansn.appcomponentsplayground.activity.intent.IntentsActivity
import ir.erfansn.appcomponentsplayground.activity.launchmode.LaunchModeActivity
import ir.erfansn.appcomponentsplayground.databinding.ActivityOwnBinding
import ir.erfansn.appcomponentsplayground.databinding.SimpleListItemBinding
import kotlin.random.Random

class OwnActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnBinding

    private var randomNumber = Random.nextInt(1, Short.MAX_VALUE.toInt())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.generatedRandomNum.text = resources.getQuantityString(
            R.plurals.random_number,
            savedInstanceState?.getInt(KEY_RANDOM_NUM) ?: 0,
            "onCreate",
            savedInstanceState?.getInt(KEY_RANDOM_NUM)?.also {
                randomNumber = it
            }
        )

        val itemList = List(40, Int::toString)
        binding.recyclerView.adapter = SimpleRecyclerViewAdapter(itemList)

        binding.startActivity.setOnClickListener {
            startActivity(Intent(this, IntentsActivity::class.java))
        }
        binding.launchModes.setOnClickListener {
            startActivity(Intent(this, LaunchModeActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Current process & activity state is Visible (no focus), Started")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "Activity' InstantState restored RandomNumber=${savedInstanceState.getInt(
            KEY_RANDOM_NUM)}")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "Current process & activity state is Foreground (focus), Resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "Current process & activity state is Visible (no focus), Paused")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "Current process & activity state is Background (Invisible), Stopped")
    }

    /**
     * This method in `Android P` called after [onStop] callback but for earlier version
     * is called before [onStop] and there's no guarantee about that called after or before [onPause].
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(
            KEY_RANDOM_NUM,
            randomNumber
        )
        super.onSaveInstanceState(outState)
        Log.i(TAG, "Activity's InstanceState saved")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "Activity restarted")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Current process & activity state is Empty (Configuration changes or finish), Destroyed")
    }

    companion object {
        private const val KEY_RANDOM_NUM = "random_number"

        private val TAG = OwnActivity::class.simpleName
    }
}

private class SimpleRecyclerViewAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<SimpleRecyclerViewAdapter.SimpleViewHolder>() {

    class SimpleViewHolder(
        private val binding: SimpleListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(index: String) {
            binding.index.text = index
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SimpleViewHolder {
        return SimpleViewHolder(
            SimpleListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(index = items[position])
    }

    override fun getItemCount(): Int = items.size
}
