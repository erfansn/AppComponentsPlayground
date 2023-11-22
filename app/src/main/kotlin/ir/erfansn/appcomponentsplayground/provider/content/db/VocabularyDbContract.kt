package ir.erfansn.appcomponentsplayground.provider.content.db

import android.provider.BaseColumns

object VocabularyDbContract {

    object WordsEntry : BaseColumns {
        const val TABLE_NAME = "words"
        const val WORD = "word"
        const val FREQUENCY = "frequency"
    }
}
