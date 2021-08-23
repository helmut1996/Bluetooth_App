package com.example.bluetooth_app

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

private lateinit var  tvStatus:TextView
private lateinit var tvDispositivos:TextView
private lateinit var btnEncendido:Button
private lateinit var btnApagado:Button
private lateinit var btnVisibilidad:Button
private lateinit var btnDispositivos:Button
private lateinit var ImageB:ImageView

private  val RESQUEST_CODE_ENABLE_BT:Int =1
private val RESQUEST_CODE_DISCOVERABLE_BT:Int=2
// adapter bluetooth
lateinit var bAdapter:BluetoothAdapter
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //vinculando la Vista
        tvStatus=findViewById(R.id.tv_bluetooth_status)
        btnEncendido=findViewById(R.id.btn_bluetooth_on)
        btnApagado=findViewById(R.id.btn_bluetooth_off)
        btnVisibilidad=findViewById(R.id.btn_bluetooth_discoverable)
        btnDispositivos=findViewById(R.id.btn_bluetooth_drives)
        tvDispositivos=findViewById(R.id.pairedtv)
        ImageB=findViewById(R.id.imageView)

        //iniciamos el adaptador
    bAdapter= BluetoothAdapter.getDefaultAdapter()

        // Revisamos si el Bluetooth esta habilitado
        if (bAdapter==null){
            tvStatus.text="Bluetooth no esta Habilitado"
        }else{
           // tvStatus.text="Bluetooth habilitado"
        }

        //mostrando imagen bluetooth dependiendo su estado Encendido/Apagado
      /*
        if (bAdapter.isEnabled){
            ImageB.setImageResource(R.drawable.b_on)
        }else{
            ImageB.setImageDrawable(R.drawable.b_off)
        }

       */

        btnEncendido.setOnClickListener {
            if(bAdapter.isEnabled){
                Toast.makeText(this,"Encendido",Toast.LENGTH_LONG).show()
            }else{

                val intent=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent,RESQUEST_CODE_ENABLE_BT)
            }
        }

        btnApagado.setOnClickListener {
            if(!bAdapter.isEnabled){
                Toast.makeText(this,"Apagado",Toast.LENGTH_LONG).show()
                tvDispositivos.text=" "
            }else{
                bAdapter.disable()
                Toast.makeText(this,"Apagado",Toast.LENGTH_LONG).show()
            }
        }

        btnVisibilidad.setOnClickListener {
            if(bAdapter.isDiscovering){
                Toast.makeText(this,"Hacer el Dispositivo sea Visible",Toast.LENGTH_LONG).show()
                val intent=Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, RESQUEST_CODE_DISCOVERABLE_BT)
            }
        }

        btnDispositivos.setOnClickListener {

            if (bAdapter.isEnabled){
                tvDispositivos.text="Dispositivos Encontrados"
                // mostrando la lista de los Dispositivos
                val devices= bAdapter.bondedDevices
                for (device in devices){
                    val deviceName= device.name
                    val deviceAddress= device

                    tvDispositivos.append("\nDivice: $deviceName , $devices" )
                }

            }else{
                Toast.makeText(this,"Primero debe encender el Bluetooth",Toast.LENGTH_LONG).show()

            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
            RESQUEST_CODE_ENABLE_BT->
                if (resultCode==Activity.RESULT_OK){
                    Toast.makeText(this,"Bluetooth Apagado!!",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Blutooth Encendido!!",Toast.LENGTH_LONG).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}