package au.com.pro.note.pad.notepad.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import au.com.pro.note.pad.notepad.data.model.Notepad
import au.com.pro.note.pad.notepad.repository.NotepadRepository

class NotepadViewModel(application: Application) : AndroidViewModel(application) {

    private var noteList : LiveData<List<Notepad>>
    private val notepadRepo: NotepadRepository

    init {
        notepadRepo = NotepadRepository(application)
        noteList = notepadRepo.getNotesList()
    }

    fun getAllNotesList() : LiveData<List<Notepad>> {
        return noteList
    }

    fun addNotes(notepad: Notepad) {
        notepadRepo.addNotes(notepad)
    }

}
