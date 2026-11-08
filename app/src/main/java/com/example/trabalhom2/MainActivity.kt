package com.example.trabalhom2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var operandoA: EditText
    private lateinit var operandoB: EditText
    private lateinit var resultado: TextView
    private lateinit var btnCompartilhar: Button
    private lateinit var botoes: List<Button>

    // Guarda os dados do último cálculo para compartilhar
    private var mensagemCompartilhar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        operandoA = findViewById(R.id.operandoA)
        operandoB = findViewById(R.id.operandoB)
        resultado = findViewById(R.id.resultado)
        btnCompartilhar = findViewById(R.id.btnCompartilhar)

        val btnSomar = findViewById<Button>(R.id.btnSomar)
        val btnSubtrair = findViewById<Button>(R.id.btnSubtrair)
        val btnMultiplicar = findViewById<Button>(R.id.btnMultiplicar)
        val btnDividir = findViewById<Button>(R.id.btnDividir)
        botoes = listOf(btnSomar, btnSubtrair, btnMultiplicar, btnDividir)

        btnSomar.setOnClickListener { calcular("mais") { a, b -> a + b } }
        btnSubtrair.setOnClickListener { calcular("menos") { a, b -> a - b } }
        btnMultiplicar.setOnClickListener { calcular("vezes") { a, b -> a * b } }
        btnDividir.setOnClickListener { calcular("dividido por") { a, b -> a / b } }

        btnCompartilhar.setOnClickListener { compartilhar() }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                atualizarBotoes()
                // Os operandos mudaram: esconde o resultado anterior de compartilhar
                ocultarCompartilhar()
            }
        }
        operandoA.addTextChangedListener(watcher)
        operandoB.addTextChangedListener(watcher)

        atualizarBotoes()
    }

    private fun atualizarBotoes() {
        val habilitado = operandoA.text.isNotBlank() && operandoB.text.isNotBlank()
        botoes.forEach { it.isEnabled = habilitado }
    }

    private fun calcular(op: String, operacao: (Double, Double) -> Double) {
        val a = operandoA.text.toString().toDoubleOrNull()
        val b = operandoB.text.toString().toDoubleOrNull()
        if (a == null || b == null) {
            ocultarCompartilhar()
            resultado.text = "<resultado>"
            return
        }
        val s = operacao(a, b)
        resultado.text = s.toString()
        mensagemCompartilhar = "Oi! Você sabia que $a $op $b é igual a $s?"
        btnCompartilhar.visibility = View.VISIBLE
    }

    private fun ocultarCompartilhar() {
        mensagemCompartilhar = null
        btnCompartilhar.visibility = View.GONE
        resultado.text = "<resultado>"
    }

    private fun compartilhar() {
        val mensagem = mensagemCompartilhar ?: return
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, mensagem)
        }
        startActivity(Intent.createChooser(intent, "Compartilhar resultado"))
    }
}
