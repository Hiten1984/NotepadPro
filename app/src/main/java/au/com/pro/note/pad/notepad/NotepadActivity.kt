package au.com.pro.note.pad.notepad

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import au.com.pro.note.pad.notepad.adapter.NotepadAdapter
import au.com.pro.note.pad.notepad.data.dao.NotepadDao
import au.com.pro.note.pad.notepad.data.db.NotepadDatabase
import au.com.pro.note.pad.notepad.data.model.Notepad
import au.com.pro.note.pad.notepad.viewmodel.NotepadViewModel

import kotlinx.android.synthetic.main.activity_notepad.*
import kotlinx.android.synthetic.main.content_notepad.*

class NotepadActivity : AppCompatActivity(), NotepadAdapter.OnItemClickListener {
    override fun onItemClick(notepad: Notepad) {
        var intent = Intent(applicationContext, NotepadDetailsActivity::class.java)
        intent.putExtra("notepad_Id", notepad.id)
        startActivity(intent)
    }

    private lateinit var notepadDatabase: NotepadDatabase

    private lateinit var notepadDao: NotepadDao

    private lateinit var notesList: LiveData<List<Notepad>>

    private lateinit var viewModel: NotepadViewModel

    private var notepadAdapter: NotepadAdapter? = null

    private var notepadList: List<Notepad>?  = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notepad)
        setSupportActionBar(toolbar)

        notepadDatabase = NotepadDatabase.getNotePadDB(this)
        notepadDao = notepadDatabase.notepadDao()
        notesList = notepadDao.getAllNotes()

//        invalidateOptionsMenu()

        notepadAdapter = NotepadAdapter(arrayListOf(), this)
        notepad_list_items.layoutManager = LinearLayoutManager(this)
        notepad_list_items.adapter = notepadAdapter

        viewModel = ViewModelProviders.of(this).get(NotepadViewModel::class.java)

        viewModel.getNotesList().observe(
                this,
                Observer { notepad: List<Notepad>? ->
                    notepadList = notepad
                    welcomeCheck(notepad)
                    notepadAdapter!!.addNotes(notepad!!)
                })

        fab.setOnClickListener { view ->
            openDetailsActivity()
        }
    }

    private fun openDetailsActivity() {
        var intent = Intent(applicationContext, NotepadDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_notepad, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menu_add_note -> {
                openDetailsActivity()
                true
            }
            R.id.menu_delete_all -> {
                deleteAllNotes()
                true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllNotes() {
        notepadDao.deleteAllNotes()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        if(notepadList!!.isEmpty())
            menu.findItem(R.id.menu_delete_all).isVisible = false
        return true
    }

    private fun welcomeCheck(notepad: List<Notepad>?) {
        if(notepad!!.isEmpty()) {
            notepad_list_items.visibility = View.GONE
            notepad_welcome_text.visibility = VISIBLE
        } else {
            notepad_list_items.visibility = View.VISIBLE
            notepad_welcome_text.visibility = GONE
        }
    }

}
