package ir.erfansn.appcomponentsplayground.provider.content

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import ir.erfansn.appcomponentsplayground.provider.content.db.VocabularyDataBaseHelper
import ir.erfansn.appcomponentsplayground.provider.content.db.VocabularyDbContract

// https://cs.android.com/android/platform/superproject/main/+/main:development/samples/NotePad/src/com/example/android/notepad/NotePadProvider.java;l=516;bpv=0
class VocabularyProvider : ContentProvider() {

    private lateinit var vocabularyDataBaseHelper: VocabularyDataBaseHelper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(
            VocabularyProviderContract.AUTHORITY,
            VocabularyDbContract.WordsEntry.TABLE_NAME,
            WORDS
        )
        addURI(
            VocabularyProviderContract.AUTHORITY,
            "${VocabularyDbContract.WordsEntry.TABLE_NAME}/#",
            WORDS_ID
        )
    }

    override fun onCreate(): Boolean {
        vocabularyDataBaseHelper = VocabularyDataBaseHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        val adjustedSelection: String?
        val adjustedSelectionArgs: Array<out String>?
        when (uriMatcher.match(uri)) {
            WORDS -> {
                adjustedSelection = selection
                adjustedSelectionArgs = selectionArgs
            }

            WORDS_ID -> {
                val id = ContentUris.parseId(uri)
                adjustedSelection = "${
                    selection?.let { "$it, " }.orEmpty()
                }${VocabularyProviderContract.Words._ID} = ?"
                adjustedSelectionArgs = arrayOf(*selectionArgs.orEmpty(), id.toString())
            }

            else -> {
                throw IllegalArgumentException()
            }
        }

        /*
         SQLite3 is thread-safe by default, no need to synchronize. [https://stackoverflow.com/questions/6675240/is-sqlite-database-instance-thread-safe]
         Database instance closes automatically when the hosting process is killed.
         */
        return vocabularyDataBaseHelper.readableDatabase.query(
            VocabularyDbContract.WordsEntry.TABLE_NAME,
            projection,
            adjustedSelection,
            adjustedSelectionArgs,
            null,
            null,
            sortOrder,
        ).apply {
            setNotificationUri(context?.contentResolver, uri)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        require(uriMatcher.match(uri) == WORDS)

        val newRow = vocabularyDataBaseHelper.writableDatabase.insert(
            VocabularyDbContract.WordsEntry.TABLE_NAME,
            null,
            values
        ).also { rows ->
            if (rows > 0) context?.contentResolver?.notifyChange(uri, null)
        }
        return newRow.takeIf { it != -1L }?.let {
            ContentUris.withAppendedId(VocabularyProviderContract.Words.CONTENT_URI, it)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val adjustedSelection: String?
        val adjustedSelectionArgs: Array<out String>?
        when (uriMatcher.match(uri)) {
            WORDS -> {
                adjustedSelection = selection
                adjustedSelectionArgs = selectionArgs
            }

            WORDS_ID -> {
                val id = ContentUris.parseId(uri)
                adjustedSelection = "${
                    selection?.let { "$it, " }.orEmpty()
                }${VocabularyProviderContract.Words._ID} = ?"
                adjustedSelectionArgs = arrayOf(*selectionArgs.orEmpty(), id.toString())
            }

            else -> {
                throw IllegalArgumentException()
            }
        }

        return vocabularyDataBaseHelper.writableDatabase.delete(
            VocabularyDbContract.WordsEntry.TABLE_NAME,
            adjustedSelection,
            adjustedSelectionArgs,
        ).also { rows ->
            if (rows > 0) context?.contentResolver?.notifyChange(uri, null)
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        val adjustedSelection: String?
        val adjustedSelectionArgs: Array<out String>?
        when (uriMatcher.match(uri)) {
            WORDS -> {
                adjustedSelection = selection
                adjustedSelectionArgs = selectionArgs
            }

            WORDS_ID -> {
                val id = ContentUris.parseId(uri)
                adjustedSelection = "${
                    selection?.let { "$it, " }.orEmpty()
                }${VocabularyProviderContract.Words._ID} = ?"
                adjustedSelectionArgs = arrayOf(*selectionArgs.orEmpty(), id.toString())
            }

            else -> {
                throw IllegalArgumentException()
            }
        }

        return vocabularyDataBaseHelper.writableDatabase.update(
            VocabularyDbContract.WordsEntry.TABLE_NAME,
            values,
            adjustedSelection,
            adjustedSelectionArgs,
        ).also { rows ->
            if (rows > 0) context?.contentResolver?.notifyChange(uri, null)
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    companion object {
        private const val WORDS = 1
        private const val WORDS_ID = 2
    }
}
