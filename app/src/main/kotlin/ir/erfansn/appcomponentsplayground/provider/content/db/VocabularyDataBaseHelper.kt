package ir.erfansn.appcomponentsplayground.provider.content.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import org.intellij.lang.annotations.Language

class VocabularyDataBaseHelper(context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION,
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

    companion object {
        private const val DATABASE_NAME = "vocabulary.db"
        private const val DATABASE_VERSION = 1

        @Language("sql")
        private val SQL_CREATE_ENTRIES = """
            CREATE TABLE ${VocabularyDbContract.WordsEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY,
                ${VocabularyDbContract.WordsEntry.WORD} TEXT,
                ${VocabularyDbContract.WordsEntry.FREQUENCY} INTEGER
            )
        """.trimIndent().intoSingleLine()
    }
}

private fun String.intoSingleLine(): String {
    return replace("\n( {4})?".toRegex(), "")
}
