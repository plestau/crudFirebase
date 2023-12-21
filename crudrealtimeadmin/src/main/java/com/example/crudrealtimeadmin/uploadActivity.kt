package com.example.crudrealtimeadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crudrealtimeadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.Year

class uploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // verifica que el boton saveButton no es nulo
        println(binding.saveButton)

        binding.saveButton.setOnClickListener{
            val title = binding.uploadTitle.text.toString()
            val author = binding.uploadAuthorName.text.toString()
            val genre = binding.uploadGenre.text.toString()
            val year = binding.uploadYear.text.toString()
            val resume = binding.uploadResume.text.toString()

            val bookData = BookData(title, author, genre, year, resume)
            databaseReference = FirebaseDatabase.getInstance().getReference("BookData")
            databaseReference.child(title).setValue(bookData).addOnSuccessListener {
                binding.uploadTitle.text.clear()
                binding.uploadAuthorName.text.clear()
                binding.uploadGenre.text.clear()
                binding.uploadYear.text.clear()
                binding.uploadResume.text.clear()

                Toast.makeText(this, "Libro subido a la bbdd", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@uploadActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener(){
                Toast.makeText(this, "Error al subir el libro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}