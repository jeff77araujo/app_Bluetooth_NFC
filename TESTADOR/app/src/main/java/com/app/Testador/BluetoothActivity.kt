package com.app.Testador

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.testenfc.R
import kotlinx.android.synthetic.main.activity_bluetooth.*

class BluetoothActivity : AppCompatActivity() {

    private val REQUEST_CODE_ENABLE_BLUETOOTH: Int = 1
    private val REQUEST_CODE_DISCOVERABLE_BLUETOOTH: Int = 2

    //BluetoothAdapter
    lateinit var blueAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        if (supportActionBar != null) { supportActionBar!!.hide() }

        // Inicialização do adaptador bluetooth
        blueAdapter = BluetoothAdapter.getDefaultAdapter()

        // Alterar o ícone dependendo do status quando o aplicativo é iniciado
        if (blueAdapter.isEnabled) {
            imgStatus.setImageResource(R.drawable.b_on)
        }
        if (!blueAdapter.isEnabled) {
            imgStatus.setImageResource(R.drawable.b_off)
        }

        // Verifique se tem Bluetooth no aparelho
        if (blueAdapter == null) {
            status.text = "Bluetooth não está disponível neste dispositivo"
        } else {
            status.text = "Bluetooth está disponível neste dispositivo"
        }

        // Botão de ligar o bluetooth
        btnOn.setOnClickListener {
            if (blueAdapter.isEnabled) {
                // Se já estiver ativado
                Toast.makeText(this, "Bluetooth já está ativado", Toast.LENGTH_LONG).show()
            } else {
                // Ligue o Bluetooth
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BLUETOOTH)
            }

        }

        // Botão para desligar o Bluetooth
        btnOff.setOnClickListener {
            if (!blueAdapter.isEnabled) {
                //Se já estiver desabilitado
                Toast.makeText(this, "Bluetooth já está desabilitado", Toast.LENGTH_LONG).show()
            } else {
                //Desligue o Bluetooth
                blueAdapter.disable()
                imgStatus.setImageResource(R.drawable.b_off)
                Toast.makeText(this, "Bluetooth foi desativado", Toast.LENGTH_LONG).show()
            }
        }

        // Botão de mostrar o Bluetooth
        btnOcultar.setOnClickListener {
            if (!blueAdapter.isDiscovering) {
                Toast.makeText(this, "Tornando o dispositivo visível...", Toast.LENGTH_LONG).show()
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BLUETOOTH)

            }
        }

        //Botão de dispositivos pareados
        btnPareamento.setOnClickListener {
            txtDevices.setText("")
            if (blueAdapter.isEnabled) {
                //lista de dipositivos
                val devices = blueAdapter.bondedDevices
                for (device in devices) {
                    val name = device.name
                    val address = device.address

                    txtDevices.append(" \n Nome: $name - $address \n--------------------------------------------------------------------\n")
                }
            } else {
                Toast.makeText(this, "Primeiro ative o bluetooth", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ENABLE_BLUETOOTH ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth foi ativado", Toast.LENGTH_LONG).show()
                    imgStatus.setImageResource(R.drawable.b_on)
                } else {
                    //Se o usuário negar a confirmação da caixa de diálogo
                    Toast.makeText(this, "Não foi possível ativar o bluetooth", Toast.LENGTH_LONG).show()
                    imgStatus.setImageResource(R.drawable.b_off)
                }
        }

        if (blueAdapter.isEnabled) {
            imgStatus.setImageResource(R.drawable.b_on)
        }
        if (!blueAdapter.isEnabled) {
            imgStatus.setImageResource(R.drawable.b_off)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}