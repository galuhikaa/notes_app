package com.mobile.notesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.notesapp.R
import com.mobile.notesapp.data.Note

class NotesAdapter(private var note : List<Note>, context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titletextview : TextView = itemView.findViewById(R.id.title_view)
        val desctextview : TextView = itemView.findViewById(R.id.desc_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = note.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = note[position]
        holder.titletextview.text = note.title
        holder.desctextview.text = note.description
    }

    fun refresh (newNote: List<Note>) {
        note = newNote
        notifyDataSetChanged()
    }
}