package com.example.bussormark

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val preferencias = getSharedPreferences("name_formulary", Context.MODE_PRIVATE)
        val peso: EditText = findViewById<EditText>(R.id.Peso)
        val estatura: EditText = findViewById<EditText>(R.id.Estatura)

        val resultado: TextView = findViewById<TextView>(R.id.resultado)

        val button_submit: Button = findViewById<Button>(R.id.button_submit)
        val boton_registro: Button = findViewById<Button>(R.id.boton_registro)
        val button_historico: Button = findViewById<Button>(R.id.button_historico)

        if (preferencias.getString("name_formulary", "") != "") {
            var nombre = preferencias.getString("name_formulary", "")
            var hello = resultado.text.toString()
            nombre = hello + " " + nombre
            resultado.setText(nombre)

        }

        boton_registro.setOnClickListener {
            val intento_formulario = Intent(this, Formulario::class.java)
            startActivity(intento_formulario)
        }

        button_submit.setOnClickListener {
            val pesoTexto = peso.text.toString()
            val estaturaTexto = estatura.text.toString()

            if (pesoTexto.isEmpty() || estaturaTexto.isEmpty()) {
                resultado.text = "Ingresa un valor de peso y estatura válido."
                return@setOnClickListener
            }

            val pesoDouble = pesoTexto.toDoubleOrNull()
            val estaturaDouble = estaturaTexto.toDoubleOrNull()

            if (pesoDouble == null || estaturaDouble == null || estaturaDouble <= 0.0) {
                resultado.text = "Ingresa un valor de peso y estatura válido."
                return@setOnClickListener
            }

            val imc = pesoDouble / (estaturaDouble * estaturaDouble)
            val clasificacion: String = when {
                imc < 18.5 -> "Bajo peso"
                imc < 24.9 -> "Normal"
                imc < 29.9 -> "Sobrepeso"
                imc < 34.9 -> "Obesidad (Clase I)"
                imc < 39.9 -> "Obesidad (Clase II)"
                else -> "Obesidad (Clase III)"
            }

            val respuesta = "Tu IMC es: ${"%.2f".format(imc)}. Estado: $clasificacion"
            resultado.text = respuesta

            // Guardar en la base de datos
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            val fechaActual = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())

            val registro = android.content.ContentValues()
            registro.put("IMC", "%.2f".format(imc))
            registro.put("Categoria", clasificacion)
            registro.put("Fecha", fechaActual)

            bd.insert("resultado_historico", null, registro)
            bd.close()
        }

        button_historico.setOnClickListener {
            val intento_historico = Intent(this, Historico::class.java)
            startActivity(intento_historico)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}