package au.com.pro.note.pad.notepad

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import au.com.pro.note.pad.notepad.data.dao.NotepadDao
import au.com.pro.note.pad.notepad.data.db.NotepadDatabase
import au.com.pro.note.pad.notepad.data.model.Notepad
import au.com.pro.note.pad.notepad.viewmodel.NotepadViewModel
import kotlinx.android.synthetic.main.activity_notepad.*
import kotlinx.android.synthetic.main.notepad_details_activity.*

class NotepadDetailsActivity : AppCompatActivity() {

    private lateinit var notepadDatabase: NotepadDatabase

    private lateinit var notepadDao: NotepadDao

    private var currentNote: Int? = null
    var editNote: Notepad? = null
    private lateinit var viewModel: NotepadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notepad_details_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        notepadDatabase = NotepadDatabase.getNotePadDB(this)
        notepadDao = notepadDatabase.notepadDao()

        viewModel = ViewModelProviders.of(this).get(NotepadViewModel::class.java)
        currentNote = intent.getIntExtra("notepad_Id", -1)

        if(currentNote != -1) {
            setTitle(R.string.edit_notepad)
            editNote = notepadDao.getNotesById(currentNote!!)
            notepad_text_pad.setText(editNote!!.text)
        } else {
            setTitle(R.string.add_notepad)
            invalidateOptionsMenu()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.done_item -> {
                if(currentNote == -1) {
                    saveNote()
                } else {
                    updateNote()
                }
                finish()
            }
            R.id.delete_item -> {
                deleteNote()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        notepadDao.deleteNote(editNote!!)
    }

    private fun saveNote() {
        var input = notepad_text_pad.text.toString()
        var note = Notepad(0, input, System.currentTimeMillis())
        viewModel.addNotes(note)
    }

    private fun updateNote() {
        var input = notepad_text_pad.text.toString()
        var note = Notepad(editNote!!.id, input, System.currentTimeMillis())
        notepadDao.updateNote(note)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        if(currentNote == -1) {
            menu.findItem(R.id.delete_item).isVisible = false
        }
        return true
    }
}
