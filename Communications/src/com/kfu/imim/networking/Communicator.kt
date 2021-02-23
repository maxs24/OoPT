package com.kfu.imim.networking

import java.io.*
import java.net.Socket
import java.net.SocketException
import kotlin.concurrent.thread

class Communicator(
    private var socket: Socket
) {

    private var active: Boolean
    private var communicationProcess: Thread? = null
    private var dataReceivedListeners = mutableListOf<(String)->Unit>()
    val isAlive: Boolean
        get() = active && socket.isConnected
    init {
        active = true
    }

    fun addDataReceivedListener(l: (String)->Unit){
        dataReceivedListeners.add(l)
    }
    fun removeDataReceivedListener(l: (String)->Unit){
        dataReceivedListeners.remove(l)
    }

    private fun communicate(){
        while (isAlive){
            try{
                val value = receiveData()
                if (value!=null) {
                    dataReceivedListeners.forEach {
                        it.invoke(value)
                    }
                }
            } catch (e: SocketException){
                active = false
                if (!socket.isClosed) socket.close()
                println("Обмен данными неожиданно прекращен!")
            }
        }
    }

    private fun receiveData(): String? {
        var data: String? = null
        if (isAlive){
            try{
                val br = BufferedReader(InputStreamReader(socket.getInputStream()))
                data = br.readLine()
            } catch (e: SocketException){
                println("Не удалось прочитать данные из сети")
                active = false
                data = null
            }
        }
        return data
    }

    fun sendData(data: String){
        try {
            if (isAlive){
                val pw = PrintWriter(socket.getOutputStream())
                pw.println(data)
                pw.flush()
            }
        } catch (e: SocketException){
            println("Не удалось отправить данные в сеть")
            active = false
        }
    }

    fun start(){
        if (communicationProcess?.isAlive == true)
            stop()
        active = true
        communicationProcess = thread{
            communicate()
        }
    }

    fun stop(){
        try{
            active = false
            if (communicationProcess?.isAlive == true){
                communicationProcess?.interrupt()
            }
        } catch (e: InterruptedException) {
            communicationProcess?.join()
        }
        finally {
            if (!socket.isClosed) socket.close()
        }
    }
}