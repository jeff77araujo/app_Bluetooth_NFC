package com.app.Testador

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.testenfc.R

class TesteActivity : AppCompatActivity() {
    lateinit var nfcAdapter : NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)

        val isNfcSupported: Boolean =
            this.nfcAdapter != null
    }
}

class OutcomingNfcManager(
    private val nfcActivity: NfcActivity
) :
    NfcAdapter.CreateNdefMessageCallback,
    NfcAdapter.OnNdefPushCompleteCallback {

    override fun createNdefMessage(event: NfcEvent): NdefMessage {
        // creating outcoming NFC message with a helper method
        // you could as well create it manually and will surely need, if Android version is too low
        val outString = nfcActivity.getOutcomingMessage()

        with(outString) {
            val outBytes = this.toByteArray()
            val outRecord = NdefRecord.createMime("MIME_TEXT_PLAIN", outBytes)
            return NdefMessage(outRecord)
        }
    }

    override fun onNdefPushComplete(event: NfcEvent) {
        // onNdefPushComplete() is called on the Binder thread, so remember to explicitly notify
        // your view on the UI thread
        nfcActivity.signalResult()
    }


    /*
    * Callback to be implemented by a Sender activity
    * */
    interface NfcActivity {
        fun getOutcomingMessage(): String

        fun signalResult()
    }
}