package com.example.bussormark

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

        val peso: EditText = findViewById<EditText>(R.id.Peso)
        val estatura: EditText = findViewById<EditText>(R.id.Estatura)

        val resultado: TextView = findViewById<TextView>(R.id.resultado)

        val button_submit: Button = findViewById<Button>(R.id.button_submit)
        val boton_registro=findViewById<Button>(R.id.boton_registro)

        

        boton_registro.setOnClickListener {
            val intento_formulario = Intent(this, Formulario::class.java)
            startActivity(intento_formulario)
        }

        button_submit.setOnClickListener {
            val pesoDouble = peso.text.toString().toDouble()
            val estaturaDouble = estatura.text.toString().toDouble()

            if (estaturaDouble > 0.0) {
                val imc = pesoDouble / (estaturaDouble * estaturaDouble)
                val clasificacion: String

                when {
                    imc < 18.5 -> clasificacion = "Bajo peso"
                    imc >= 18.5 && imc < 24.9 -> clasificacion = "Normal"
                    imc >= 25.0 && imc < 29.9 -> clasificacion = "Sobrepeso"
                    imc >= 30.0 && imc < 34.9 -> clasificacion = "Obesidad (Clase I)"
                    imc >= 35.0 && imc < 39.9 -> clasificacion = "Obesidad (Clase II)"
                    else -> clasificacion = "Obesidad (Clase III)"
                }
                val respuesta = "Tu IMC es: ${"%.2f".format(imc)}. Estado: $clasificacion"
                resultado.text = respuesta
            } else {
                val respuesta = "Ingresa una estatura válida."
                resultado.text = respuesta
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}