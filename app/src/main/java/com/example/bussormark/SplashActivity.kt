package com.example.bussormark

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val formulary = getSharedPreferences("name_formulary", Context.MODE_PRIVATE)

        val star = findViewById<ImageView>(R.id.star_icon)
        star.animate().rotationBy(360f).setDuration(2500).start()

        Handler(Looper.getMainLooper()).postDelayed({
            if(formulary == null || formulary.getString("name_formulary", "") == ""){
                startActivity(Intent(this, Formulario::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2500)
    }
}