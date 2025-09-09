package com.example.bussormark

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Historico : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historico)

        val preferencias = getSharedPreferences("name_formulary", Context.MODE_PRIVATE)

        val nombre_usuario=findViewById<TextView>(R.id.nombre_usuario)
        val resultado_historico = findViewById<TextView>(R.id.resultado_historico)
        val button_back = findViewById<Button>(R.id.button_back)
        val button_clear = findViewById<Button>(R.id.button_clear)

        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.readableDatabase

        val fila = bd.rawQuery("SELECT IMC, Categoria, Fecha FROM resultado_historico", null)

        var historial = ""

        if (preferencias.getString("name_formulary", "") != "") {
            var nombre = preferencias.getString("name_formulary", "")
            nombre = "Usuario: $nombre"
            nombre_usuario.setText(nombre)
        }

        if (fila.moveToFirst()) {
            do {
                val imc = fila.getString(0)
                val categoria = fila.getString(1)
                val fecha = fila.getString(2)
                historial += "IMC: $imc | $categoria | $fecha\n"
            } while (fila.moveToNext())
        }

        resultado_historico.text = historial
        fila.close()
        bd.close()

        button_back.setOnClickListener {
            finish()
        }

        button_clear.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            bd.delete("resultado_historico", null, null) // Elimina todos los registros
            bd.close()

            resultado_historico.text = "Historial eliminado."
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}