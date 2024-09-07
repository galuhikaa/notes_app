package com.mobile.notesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.notesapp.data.Note
import com.mobile.notesapp.data.NoteDatabaseHelper
import com.mobile.notesapp.databinding.ActivityDetailBinding

class Detail : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var db : NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val noteId = intent.getIntExtra("id", 1)

        if (noteId != -1) {
            fetchNoteDetails(db.getDataNotes(), noteId)
        } else {
            Toast.makeText(this, "ID tidak valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchNoteDetails(notelist: List<Note>, id : Int) {
        val note = notelist.find { it.id == id }

        if (note != null) {
            binding.detailTitle.text = note.title
            binding.detailDesc.text = note.description
        } else {
            Toast.makeText(this, "Note tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }
}