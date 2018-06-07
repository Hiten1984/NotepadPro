package au.com.pro.note.pad.notepad.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import au.com.pro.note.pad.notepad.data.dao.NotepadDao
import au.com.pro.note.pad.notepad.data.db.NotepadDatabase
import au.com.pro.note.pad.notepad.data.model.Notepad

class NotepadRepository(application: Application) {

    private var notepadDao: NotepadDao? = null
    var allNotesList : LiveData<List<Notepad>>

    private val notesDb: NotepadDatabase

    init {
        notesDb = NotepadDatabase.getNotePadDB(application)
        allNotesList = notesDb.notepadDao().getAllNotes()
    }

    fun getNotesList() : LiveData<List<Notepad>> {
        return allNotesList
    }

    fun addNotes(notepad: Notepad) {
        addAsyncTask(notesDb).execute(notepad)
    }

    class addAsyncTask(notesDb: NotepadDatabase): AsyncTask<Notepad, Void, Void>() {
        private var notepadDb = notesDb

        override fun doInBackground(vararg params: Notepad): Void? {
            notepadDb.notepadDao().insertNote(params[0])
            return null
        }
    }

}