package com.mobile.notesapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.notesapp.R
import com.mobile.notesapp.data.Note
import com.mobile.notesapp.data.NoteDatabaseHelper
import com.mobile.notesapp.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var db : NoteDatabaseHelper
    private var note: Note? = null
    private val requestCodeUpdate = 1

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getIntExtra("id", 1)

        if (noteId != -1) {
            fetchNoteDetails(db.getDataNotes(), noteId)
        } else {
            Toast.makeText(this, "ID tidak valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchNoteDetails(notelist: List<Note>, id : Int) {
        val fnote = notelist.find { it.id == id }

        if (fnote != null) {
            note = fnote
            binding.detailTitle.text = fnote.title
            binding.detailDesc.text = fnote.description
        } else {
            Toast.makeText(this, "Note tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                val intentUpdate = Intent(this@DetailActivity, UpdateActivity::class.java)
                note?.let { intentUpdate.putExtra("id", it.id) }
                intentUpdate.putExtra("title", note?.title)
                intentUpdate.putExtra("content", note?.description)
                startActivityForResult(intentUpdate, requestCodeUpdate)
                return true
            }R.id.delete -> {
                Toast.makeText(this, "delete text", Toast.LENGTH_SHORT).show()
                return true
            }android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("This method is deprecated")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeUpdate && resultCode == RESULT_OK) {
            val noteId = intent.getIntExtra("id", 0)
            if (noteId != 0) {
                fetchNoteDetails(db.getDataNotes(), noteId)
            }
        }
    }
}