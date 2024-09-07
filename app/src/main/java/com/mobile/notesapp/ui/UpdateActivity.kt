package com.mobile.notesapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.notesapp.R
import com.mobile.notesapp.data.Note
import com.mobile.notesapp.data.NoteDatabaseHelper
import com.mobile.notesapp.databinding.ActivityUpdateBinding

@Suppress("DEPRECATION")
class UpdateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateBinding
    private lateinit var db : NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val noteId = intent.getIntExtra("id", 0)
        val noteTitle = intent.getStringExtra("title")
        val noteContent = intent.getStringExtra("content")

        binding.titleUpdate.setText(noteTitle)
        binding.descriptionUpdate.setText(noteContent)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveButtonUpdate.setOnClickListener {
            val newTitle = binding.titleUpdate.text.toString()
            val newDesc = binding.descriptionUpdate.text.toString()
            val updatenote = Note(noteId, newTitle, newDesc)
            db.updateNotes(updatenote)
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}