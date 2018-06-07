package au.com.pro.note.pad.notepad.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import au.com.pro.note.pad.notepad.data.db.NotepadDatabase
import au.com.pro.note.pad.notepad.data.model.Notepad

class NotepadViewModel(application: Application) : AndroidViewModel(application) {

    var noteList : LiveData<List<Notepad>>
    private val notesDb: NotepadDatabase

    init {
        notesDb = NotepadDatabase.getNotePadDB(this.getApplication())
        noteList = notesDb.notepadDao().getAllNotes()
    }

    fun getNotesList() : LiveData<List<Notepad>> {
        return noteList
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
