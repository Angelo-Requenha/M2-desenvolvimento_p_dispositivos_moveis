package com.example.trabalhom2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    // Credenciais válidas (a critério do grupo)
    private val cpfValido = "12345678900"
    private val senhaValida = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cpf = findViewById<EditText>(R.id.cpf)
        val senha = findViewById<EditText>(R.id.senha)

        findViewById<Button>(R.id.btnEntrar).setOnClickListener {
            if (cpf.text.toString() == cpfValido && senha.text.toString() == senhaValida) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("CPF ou senha inválidos.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}
