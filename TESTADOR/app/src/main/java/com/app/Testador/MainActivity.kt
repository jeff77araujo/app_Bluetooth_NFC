package com.app.Testador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.testenfc.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null) { supportActionBar!!.hide() }

        btnNfc.setOnClickListener {
            startActivity(Intent(this, NfcActivity::class.java))
        }

        btnBluetooth.setOnClickListener {
            startActivity(Intent(this, BluetoothActivity::class.java))
        }

    }
}