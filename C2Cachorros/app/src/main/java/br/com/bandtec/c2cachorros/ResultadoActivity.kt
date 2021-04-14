package br.com.bandtec.c2cachorros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultadoActivity : AppCompatActivity() {

    lateinit var tvCachorro1: TextView
    lateinit var tvCachorro2: TextView
    lateinit var tvPreco: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        tvCachorro1 = findViewById(R.id.tv_cachorro1)
        tvCachorro2 = findViewById(R.id.tv_cachorro2)

        tvPreco = findViewById(R.id.tv_preco)

        var racaCachorro1 = intent.getStringExtra("cachorro1")
        var racaCachorro2 = intent.getStringExtra("cachorro2")
        val precoCachorro1 = intent.getDoubleExtra("precoCachorro1", 0.0)
        val precoCachorro2 = intent.getDoubleExtra("precoCachorro2", 0.0)

        if (racaCachorro1.isNullOrBlank()) racaCachorro1 = getString(R.string.nao_encontrado)
        if (racaCachorro2.isNullOrBlank()) racaCachorro2 = getString(R.string.nao_encontrado)

        tvCachorro1.text = getString(R.string.cachorro_raca, 1, racaCachorro1)
        tvCachorro2.text = getString(R.string.cachorro_raca, 2, racaCachorro2)
        tvPreco.text = getString(R.string.cachorro_preco, precoCachorro1 + precoCachorro2)
    }
}