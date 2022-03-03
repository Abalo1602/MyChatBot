package com.iesmurgi.org.mychatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MensajeAdaptador(var data: List<Mensaje>) : RecyclerView.Adapter<MensajeAdaptador.MsgViewHolder>() {

    sealed class MsgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class LeftVH(view: View) : MsgViewHolder(view) {
            val mensajeBot: TextView = view.findViewById(R.id.tvMensajeBot) // (1)
        }
        class RightVH(view: View) : MsgViewHolder(view) {
            val mensaje: TextView = view.findViewById(R.id.tvMensaje)
        }
    }

    override fun getItemViewType(position: Int) = data[position].typer // (2)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder { //(3)
        val vh: MsgViewHolder
        if (viewType == Mensaje.TYPE_RECEIVED) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.mensaje_globo_bot, parent, false)
            vh = MsgViewHolder.LeftVH(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.mensaje_globo, parent, false)
            vh = MsgViewHolder.RightVH(view)
        }
        return vh
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) { //(4)
        var msg = data[position]
        when (holder) {
            is MsgViewHolder.LeftVH -> holder.mensajeBot.text = msg.content
            is MsgViewHolder.RightVH -> holder.mensaje.text = msg.content
        }
    }
}
