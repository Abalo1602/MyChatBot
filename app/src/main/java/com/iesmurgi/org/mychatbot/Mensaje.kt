package com.iesmurgi.org.mychatbot

class Mensaje(val content: String, val typer: Int) {

    companion object{
        const val TYPE_RECEIVED = 0
        const val TYPE_SEND = 1
    }
}