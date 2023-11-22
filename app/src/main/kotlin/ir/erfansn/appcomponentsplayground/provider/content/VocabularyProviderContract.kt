package ir.erfansn.appcomponentsplayground.provider.content

import android.net.Uri
import android.provider.BaseColumns
import ir.erfansn.appcomponentsplayground.provider.content.db.VocabularyDbContract
import ir.erfansn.appcomponentsplayground.provider.content.db.VocabularyDbContract.WordsEntry.TABLE_NAME

object VocabularyProviderContract {

    const val AUTHORITY = "ir.erfansn.appcomponentsplayground.provider"

    object Words : BaseColumns {
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

        const val _ID = "_id"
        const val _COUNT = "_count"
        const val WORD = VocabularyDbContract.WordsEntry.WORD
        const val FREQUENCY = VocabularyDbContract.WordsEntry.FREQUENCY
    }
}
