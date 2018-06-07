package au.com.pro.note.pad.notepad.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import au.com.pro.note.pad.notepad.data.dao.NotepadDao
import au.com.pro.note.pad.notepad.data.model.Notepad

@Database(entities = [(Notepad::class)], version = 1, exportSchema = false)
abstract class NotepadDatabase : RoomDatabase() {

    abstract fun notepadDao() : NotepadDao

    companion object {
        private var INSTANCE : NotepadDatabase? = null
        fun getNotePadDB(context: Context) : NotepadDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, NotepadDatabase::class.java, "notepad-db")
                        .allowMainThreadQueries().build()
            }
            return INSTANCE as NotepadDatabase
        }
    }
}