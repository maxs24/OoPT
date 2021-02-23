package com.kfu.imim.networking

import ArgsParser
import java.net.ServerSocket
import java.net.Socket
import java.sql.*
import java.util.*
import kotlin.concurrent.thread
import kotlin.system.exitProcess
//Класс сервера
//TODO(Добавить оптимизатор задач)
//TODO(Сделать реакцию на завершение задач)
//TODO(Сделать ответы на запросы клиентов)
//TODO(Особенно сделать ответ на запрос балансировщика)

class Server private constructor(argsParser: ArgsParser){

     companion object {
        private var srv: Server? = null
        fun getInstance(argsParser: ArgsParser): Server {
            if (srv == null)
                srv = Server(argsParser)
            return srv as Server
        }
    }

    inner class ConnectedClient(socket: Socket){ //Класс подключенного клиента

        private val communicator: Communicator = Communicator(socket)
        private var name: String? = null

        init{
            communicator.addDataReceivedListener(::dataReceived)
            communicator.start()
        }

        private fun dataReceived(data: String){
            //Формат сообщений:  команда=данные
            val vls = data.split("=", limit = 2)
            if (vls.size == 2){
                when (vls[0]){ //when - это как switch на с++
                    "login" -> login(vls[1])
                    "logout" -> { communicator.sendData("goodbye") }
                   //TODO(Реакция на сообщения)
                    else -> { }
                }
            }
        }

        private fun login(data: String) {
            val (user, password) = data.split(':', limit = 2)
            //TODO(link DB)
            val rs = stmt.executeQuery("SELECT `ID` FROM users WHERE login = '$user' AND password = SHA1('$password')")
            if (rs.next()) {
                //TODO(Обработать данные пользователя из БД)

                val id = rs.getInt("ID").toString()
                val rss = stmt.executeQuery("SELECT `ID` FROM executors WHERE ID = '$id'")
                if (rss.next()) {
                    communicator.sendData("login=ok:Executor:$id")
                }
                else {
                    communicator.sendData("login=ok:Customer:$id")
                }
                name = id
            }
            else {
                communicator.sendData("login=fail")
            }
        }
    }

    //TODO(Сделать параметрами из командной строки)

    private val connectedClient = mutableListOf<ConnectedClient>() //список подключенных клиентов (онлайн)
    private val serverSocket: ServerSocket
    private var stop = false //флаг остановлен ли сервер. По умолчанию - нет
    private val db : Connection//соединение с СУБД
    private val port: Int = argsParser.serverPort
    private val host = argsParser.dbAddress
    private val dbPort = argsParser.dbPort //порт, на котором напущена СУБД
    private val dbName = argsParser.dbName //название БД в mysql
    private val stmt: Statement //выражения, отправляемые СУБД

    init{
        serverSocket = ServerSocket(port)
        //TODO(Исправить на ввод с клавиатуры)
        print("Логин пользовотеля от СУБД: ")
        val login = argsParser.dbLogin //логин от СУБД
        print("Пароль от СУБД: ")
        val psw = argsParser.dbPassword //пароль от СУБД
        val connectionProperties = Properties()
        connectionProperties["user"] = login
        connectionProperties["password"] = psw
        connectionProperties["serverTimezone"] = "UTC"
        connectionProperties["autoReconnect"] = true
        try {
            //подключаемся к СУБД
            db = DriverManager.getConnection("jdbc:mysql://$host:$dbPort/$dbName", connectionProperties)
        }
        catch (ex: Exception) {
            //ловим ошибку при создании соединения
            ex.printStackTrace()
            println("Не найдена база данных. Дальнейшная работа невозможна.")
            exitProcess(1)
        }
        stmt = db.createStatement()
        println("SERVER STARTED")
        thread {
            while (!stop) {
                acceptClient()
            }
        }
    }

    private fun acceptClient() {
        println("Ожидание подключения")
        val s = serverSocket.accept()
        println("Новый клиент подключен")
        connectedClient.add(ConnectedClient(s))
    }
}