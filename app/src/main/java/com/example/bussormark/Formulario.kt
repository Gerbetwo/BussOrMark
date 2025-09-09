package com.example.bussormark

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.core.content.edit

class Formulario : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)

        val preferencias = getSharedPreferences("name_formulary", Context.MODE_PRIVATE)

        val resultado_formulario=findViewById<EditText>(R.id.resultado_formulario)
        val button_save = findViewById<Button>(R.id.button_save)
        val button_modify = findViewById<Button>(R.id.button_modify)

        if (preferencias.getString("name_formulary", "") != "") {
            resultado_formulario.setText(preferencias.getString("name_formulary", ""))
        }

        button_save.setOnClickListener {
            if (resultado_formulario.text.toString() != "") {
                preferencias.edit(commit = true) {
                    putString("name_formulary", resultado_formulario.text.toString())
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                resultado_formulario.setText("Ingrese un nombre valido")
            }
        }

        button_modify.setOnClickListener {
            preferencias.edit(commit = true) {
                putString("name_formulary", resultado_formulario.text.toString())
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}