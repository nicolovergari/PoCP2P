package com.company.carlo.p2pcarsharing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_insert_auto.*

class InsertAuto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_auto)

        bt_conferma.setOnClickListener {

            val intent = Intent(this@InsertAuto, myCarFragment()::class.java)
            intent.putExtra("marca", text_marca.text)
            intent.putExtra("modello", text_modello.text)
            intent.putExtra("targa", text_targa.text)
            startActivity(intent)

        }
    }
}
