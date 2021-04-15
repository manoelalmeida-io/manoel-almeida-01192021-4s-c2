package br.com.bandtec.c2cachorros

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import br.com.bandtec.c2cachorros.api.Cachorro
import br.com.bandtec.c2cachorros.api.ConexaoApiCachorros
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

  private lateinit var preferencia: SharedPreferences

  lateinit var etCachorro1: EditText
  lateinit var etCachorro2: EditText

  lateinit var scAmigavel: Switch

  var id1: Int? = null
  var id2: Int? = null

  var cachorro1: Cachorro? = null
  var cachorro2: Cachorro? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    preferencia = getSharedPreferences("dados", MODE_PRIVATE)

    etCachorro1 = findViewById(R.id.et_cachorro1)
    etCachorro2 = findViewById(R.id.et_cachorro2)

    scAmigavel = findViewById(R.id.sc_amigavel)

    val id1 = preferencia.getString("id1", "")
    val id2 = preferencia.getString("id2", "")

    etCachorro1.setText(id1)
    etCachorro2.setText(id2)
  }

  fun comprar(view: View) {
    if (etCachorro1.text.isNotEmpty()) {
      id1 = etCachorro1.text.toString().toInt()
    }

    if (etCachorro2.text.isNotEmpty()) {
      id2 = etCachorro2.text.toString().toInt()
    }

    val edit = preferencia.edit()
    edit.putString("id1", id1.toString())
    edit.putString("id2", id2.toString())
    edit.apply()

    pesquisarCachorro(1, id1 ?: 0)
  }

  fun pesquisarCachorro(posicao: Int, id: Int) {
    val callback = ConexaoApiCachorros.criar().get(id)

    callback.enqueue(object : Callback<Cachorro> {
      override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {
        if (posicao == 1) {
          Log.i("response", response.toString())
          if (response.isSuccessful) {
            cachorro1 = response.body()
            Log.i("CACHORRO 1", cachorro1.toString())
            if (scAmigavel.isChecked && cachorro1?.indicadoCriancas == false) {
              cachorro1 = null
            }
          } else {
            cachorro1 = null
          }
          Log.i("CACHORRO 1", cachorro1.toString())
          pesquisarCachorro(2, id2 ?: 0)
        } else if (posicao == 2) {
          Log.i("response", response.toString())
          if (response.isSuccessful) {
            cachorro2 = response.body()
            if (scAmigavel.isChecked && cachorro2?.indicadoCriancas == false) {
              cachorro2 = null
            }
          } else {
            cachorro2 = null
          }
          Log.i("CACHORRO 2", cachorro2.toString())

          if (cachorro1 != null || cachorro2 != null) {
            val telaResultado = Intent(baseContext, ResultadoActivity::class.java)
            telaResultado.putExtra("cachorro1", cachorro1?.raca)
            telaResultado.putExtra("cachorro2", cachorro2?.raca)
            telaResultado.putExtra("precoCachorro1", cachorro1?.precoMedio?.toDouble())
            telaResultado.putExtra("precoCachorro2", cachorro2?.precoMedio?.toDouble())
            startActivity(telaResultado)
          } else {
            val telaErro = Intent(baseContext, ErroActivity::class.java)
            telaErro.putExtra("id1", id1.toString())
            telaErro.putExtra("id2", id2.toString())
            startActivity(telaErro)
          }
        }
      }

      override fun onFailure(call: Call<Cachorro>, t: Throwable) {
        Toast.makeText(baseContext, getString(R.string.erro, t.message), Toast.LENGTH_SHORT).show()
      }
    })
  }
}