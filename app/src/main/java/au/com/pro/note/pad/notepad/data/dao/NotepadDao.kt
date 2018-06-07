package au.com.pro.note.pad.notepad.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import au.com.pro.note.pad.notepad.data.model.Notepad

@Dao
interface NotepadDao {

    @Query("select * from notepad")
    fun getAllNotes() : LiveData<List<Notepad>>

    @Query("select * from notepad where notepadId in (:id)")
    fun getNotesById(id: Int) : Notepad

    @Query("delete from notepad")
    fun deleteAllNotes()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertNote(notepad: Notepad)

    @Update
    fun updateNote(notepad: Notepad)

    @Delete
    fun deleteNote(notepad: Notepad)

}