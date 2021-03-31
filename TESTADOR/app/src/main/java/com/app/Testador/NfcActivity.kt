package com.app.Testador

import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.app.testenfc.R
import kotlinx.android.synthetic.main.activity_nfc.*

class NfcActivity : AppCompatActivity() {
    lateinit var nfcAdapter : NfcAdapter
    private var androidBeamAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        if (supportActionBar != null) { supportActionBar!!.hide() }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        btnCompatibilidad.setOnClickListener {
            var res = ""
            if (nfcAdapter != null) {
                res = "O dispositivo é compatível com NFC"
            } else {
                res = "O dispositivo não é compatível com NFC"
            }
            AlertDialog.Builder(this).setMessage(res).show()
        }

        btnStatus.setOnClickListener {
            var res = ""
            if (nfcAdapter.isEnabled) {
                res = "NFC Ativado"
            } else {
                res = "NFC Desativado"
            }
            AlertDialog.Builder(this).setMessage(res).show()
        }

//        androidBeamAvailable = if (!packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)) {
//            false
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            androidBeamAvailable = false
//        } else {
//            nfcAdapter = NfcAdapter.getDefaultAdapter(this)
//            true
//        }
    }
}