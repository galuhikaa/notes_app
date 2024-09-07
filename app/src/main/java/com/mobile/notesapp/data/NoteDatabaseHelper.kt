package com.mobile.notesapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESC = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESC TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun insert(note: Note){
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DESC, note.description)
        }
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun getDataNotes() : List<Note> {
        val notelist = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val tb = db.rawQuery(query, null)
        while (tb.moveToNext()){
            val id = tb.getInt(tb.getColumnIndexOrThrow(COLUMN_ID))
            val title = tb.getString(tb.getColumnIndexOrThrow(COLUMN_TITLE))
            val desc = tb.getString(tb.getColumnIndexOrThrow(COLUMN_DESC))

            val note = Note(id, title, desc)
            notelist.add(note)
        }
        tb.close()
        db.close()
        return notelist
    }

    fun updateNotes(note : Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DESC, note.description)
        }
        val wId = "$COLUMN_ID = ? "
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, wId, whereArgs)
        db.close()
    }
}