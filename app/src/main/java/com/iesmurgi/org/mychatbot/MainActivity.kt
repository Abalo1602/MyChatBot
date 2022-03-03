package com.iesmurgi.org.mychatbot


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val data = ArrayList<Mensaje>() //(1)
    private lateinit var adapter: MensajeAdaptador //(2)

    @SuppressLint("HandlerLeak")
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) { //(3)
            when (msg.what) { //(4)
                0 -> {
                    var toString = msg.obj.toString()
                    var msg = Mensaje(toString, Mensaje.TYPE_RECEIVED)
                    data.add(msg)

                    adapter.notifyItemInserted(data.size - 1)

                    rvMensaje.scrollToPosition(data.size - 1)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        adapter = MensajeAdaptador(data)
        var linearLayoutManager = LinearLayoutManager(this)
        rvMensaje.layoutManager = linearLayoutManager
        rvMensaje.adapter = adapter
        btn_send.setOnClickListener(this)
    }

    override fun onClick(v: View?) { //(5)

        when (v) {
            btn_send -> {
                var content = etMensaje.text.toString() //(6)
                if (content.isNotEmpty()) { //(7)
                    Log.e("aaa", "size->" + data.size)
                    var msg = Mensaje(content, Mensaje.TYPE_SEND)
                    data.add(msg)
                    Log.e("aaa", "size->" + data.size)
                    adapter.notifyItemInserted(data.size - 1)

                    rvMensaje.scrollToPosition(data.size - 1)

                    etMensaje.setText("")

                    startThread(content)

                } else { //(8)
                    Toast.makeText(this, "No se puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun startThread(str: String) { //(9)
        Thread { //(10)
            Thread.sleep(1000)
            var message = Message()
            message.what = 0
            message.obj = str
            handler.sendMessage(message)
        }.start()
    }
    private fun initData() { //(11)
        data.add(Mensaje("Hola.", Mensaje.TYPE_RECEIVED))
        data.add(Mensaje("Hola, ¿quien eres?", Mensaje.TYPE_SEND))
        data.add(Mensaje("Mi nombre es Roberto y soy nuevo por aquí.", Mensaje.TYPE_RECEIVED))
        data.add(Mensaje("Vamos a jugar a un juego.", Mensaje.TYPE_RECEIVED))
        data.add(Mensaje("No me gustan los juegos y además no te conozco." , Mensaje.TYPE_SEND))
        data.add(Mensaje("Voy a repetir todo lo que digas" , Mensaje.TYPE_RECEIVED))
    }
}

