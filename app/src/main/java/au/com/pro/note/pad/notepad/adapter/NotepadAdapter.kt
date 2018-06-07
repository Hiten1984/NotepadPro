package au.com.pro.note.pad.notepad.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import au.com.pro.note.pad.notepad.R
import au.com.pro.note.pad.notepad.data.model.Notepad
import java.text.SimpleDateFormat


class NotepadAdapter(notepad: ArrayList<Notepad>, listener: OnItemClickListener) : RecyclerView.Adapter<NotepadAdapter.NotepadViewHolder>() {

    private var listNotepad: List<Notepad> = notepad

    private var listenerContact: OnItemClickListener = listener

    interface OnItemClickListener {
        fun onItemClick(notepad: Notepad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotepadViewHolder {
        return NotepadViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.notepad_item_row, parent, false))
    }

    override fun getItemCount(): Int {
        return listNotepad.size
    }

    override fun onBindViewHolder(holder: NotepadViewHolder, position: Int) {
        var currentNote: Notepad = listNotepad[position]

        holder.title.text = currentNote.text
        var date: Long = currentNote.date
        var dateString: String = SimpleDateFormat("dd MMM, YY").format(date)
        holder.date.text = dateString
//        holder.date.text = currentNote.date.toString()

        holder.bind(currentNote, listenerContact)
    }

    fun addNotes(listNotepad: List<Notepad>) {
        this.listNotepad = listNotepad
        notifyDataSetChanged()
    }


    class NotepadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.notepad_title_text)!!
        var date = itemView.findViewById<TextView>(R.id.notepad_date)!!

        fun bind(notepad: Notepad, listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClick(notepad)
            }
        }

    }
}