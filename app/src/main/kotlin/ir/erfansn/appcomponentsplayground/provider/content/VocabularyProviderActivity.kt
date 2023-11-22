package ir.erfansn.appcomponentsplayground.provider.content

import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.loader.app.LoaderManager
import androidx.loader.app.LoaderManager.LoaderCallbacks
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import ir.erfansn.appcomponentsplayground.R
import ir.erfansn.appcomponentsplayground.databinding.ActivityVocabularyBinding
import ir.erfansn.appcomponentsplayground.databinding.DialogEditWordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class VocabularyProviderActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {

    private lateinit var binding: ActivityVocabularyBinding

    private lateinit var cursorAdapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVocabularyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cursorAdapter = SimpleCursorAdapter(
            this@VocabularyProviderActivity,
            R.layout.item_word,
            null,
            arrayOf(VocabularyProviderContract.Words.WORD),
            intArrayOf(R.id.word),
            0,
        )
        binding.dictionaryWords.adapter = cursorAdapter

        // Equals with content://user_dictionary/words/4
        // to retrieve a row whose _ID is 4 from user dictionary
        val singleUri =
            ContentUris.withAppendedId(VocabularyProviderContract.Words.CONTENT_URI, 4)
        Log.i(TAG, "User dictionary uri is $singleUri")

        LoaderManager.getInstance(this).initLoader(0, null, this)

        binding.dictionaryWords.setOnItemClickListener { _, _, _, id ->
            showWordEditingDialog { enteredWord ->
                lifecycleScope.launch {
                    // Defines an object to contain the updated values
                    val updateValues = ContentValues().apply {
                        /*
                         * Sets the updated value and updates the selected words.
                         */
                        put(VocabularyProviderContract.Words.WORD, enteredWord)
                    }

                    // Defines selection criteria for the rows you want to update
                    val selectionClause = "${VocabularyProviderContract.Words._ID} == ?"
                    val selectionArgs = arrayOf(id.toString())

                    // Defines a variable to contain the number of updated rows
                    withContext(Dispatchers.IO) {
                        contentResolver.update(
                            VocabularyProviderContract.Words.CONTENT_URI,
                            updateValues,
                            selectionClause,
                            selectionArgs,
                        )
                    }
                    // This is necessary when there's no registration for ContentURI changing
                    // i.e. Cursor.setNotificationUri and ContentResolver.notifyChange
                    // LoaderManager.getInstance(this@ContentProviderActivity).restartLoader(0, null, this@ContentProviderActivity)
                    Log.i(TAG, "The row with id $id updated")
                }
            }
        }

        binding.wordInput.setEndIconOnClickListener {
            val newValues = ContentValues().apply {
                put(
                    VocabularyProviderContract.Words.WORD,
                    binding.wordInput.editText?.text.toString()
                )
                put(VocabularyProviderContract.Words.FREQUENCY, Random.nextInt(1, 256))
            }

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    contentResolver.insert(
                        VocabularyProviderContract.Words.CONTENT_URI,
                        newValues
                    )
                }?.let {
                    // LoaderManager.getInstance(this@ContentProviderActivity).restartLoader(0, null, this@ContentProviderActivity)
                    Log.i(TAG, "Newly added row id is ${ContentUris.parseId(it)}")
                } ?: run {
                    Log.i(TAG, "Insertion is failure")
                }
            }
        }
    }

    private fun showWordEditingDialog(onApply: (word: String) -> Unit) {
        val dialogLayoutBinding = DialogEditWordBinding.inflate(layoutInflater)

        AlertDialog.Builder(this)
            .setView(dialogLayoutBinding.root)
            .setPositiveButton(getString(R.string.apply)) { _, _ ->
                onApply(dialogLayoutBinding.wordInput.editText?.text.toString())
            }
            .create()
            .show()

        dialogLayoutBinding.wordInput.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.content_provider_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_clear_words -> {
            lifecycleScope.launch {
                val deletedRowsCount = withContext(Dispatchers.IO) {
                    contentResolver.delete(
                        VocabularyProviderContract.Words.CONTENT_URI,
                        null,
                        null
                    )
                }
                // LoaderManager.getInstance(this@ContentProviderActivity).restartLoader(0, null, this@ContentProviderActivity)
                Log.v(TAG, "Deleted rows count: $deletedRowsCount")
            }
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            this@VocabularyProviderActivity,
            VocabularyProviderContract.Words.CONTENT_URI,
            arrayOf(
                VocabularyProviderContract.Words._ID,
                VocabularyProviderContract.Words.WORD
            ),
            null,
            null,
            null
        ).apply {
            // Doesn't work in recent Android versions
            // setUpdateThrottle(2500)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        data?.let {
            Log.i(TAG, "There is ${it.count} words in provider")

            cursorAdapter.swapCursor(it)
        } ?: run {
            Log.e(TAG, "An unknown internal error for content provider was occurred")
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        cursorAdapter.swapCursor(null)
    }

    companion object {
        private val TAG = VocabularyProviderActivity::class.simpleName
    }
}
