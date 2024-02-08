package com.dicoding.latihansqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.latihansqllite.databinding.ActivityMainBinding
import com.dicoding.latihansqllite.model.DatabaseHandler
import com.dicoding.latihansqllite.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val db = DatabaseHandler(this)
            val user = User(
                name = binding.etNama.text.toString(),
                phoneNumber = binding.etNo.text.toString(),
                email = binding.etEmail.text.toString()
            )

            db.insert(user)
            Toast.makeText(this, "Succes add data", Toast.LENGTH_LONG).show()
        }
    }
}