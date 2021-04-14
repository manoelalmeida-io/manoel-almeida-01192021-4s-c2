package br.com.bandtec.c2cachorros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ErroActivity : AppCompatActivity() {

    lateinit var tvMensagem: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_erro)

        val id1 = intent.getStringExtra("id1")
        val id2 = intent.getStringExtra("id2")

        tvMensagem = findViewById(R.id.tv_mensagem)

        tvMensagem.text = getString(R.string.deu_ruim, id1, id2)
    }
}