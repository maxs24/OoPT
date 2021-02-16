package com.kfu.imim.networking

import com.kfu.imim.networking.Communicator
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
class Server private constructor(){

    private val serverSocket: ServerSocket
    private var stop = false //флаг остановлен ли сервер. По умолчанию - нет
    private val connectedClient = mutableListOf<ConnectedClient>() //список подключенных клиентов (онлайн)

    companion object {
        private const val PORT = 5703
        private val srv: Server = Server()

        fun getInstance(): Server {
            return srv
        }
    }

    inner class ConnectedClient(socket: Socket){ //Класс подключенного клиента

        private val communicator: Communicator
        private var name: String? = null

        init{
            communicator = Communicator(socket)
            communicator.addDataRecievedListener(::dataReceived)
            communicator.start()
        }

        private fun dataReceived(data: String){
            //Формат сообщений:  команда=данные
            val vls = data.split("=", limit = 2)
            if (vls.size == 2){
                when (vls[0]){ //when - это как switch на с++
                   //TODO(Реакция на сообщения)
                }
            }
        }

        private fun login(data: String) {
            val (user, password) = data.split('=', limit = 2)
            //TODO(link DB)
            val rs = stmt.executeQuery("SELECT * FROM users WHERE login = '$user' AND password = '$password'")
            if (rs.next()) {
                //TODO(Обработать данные пользователя из БД)
                communicator.sendData("login=ok=$user")
                name = user
            }
            else {
                communicator.sendData("login=fail")
            }
        }
    }

    private val db : Connection//соединение с СУБД
    private val host = "localhost"
    private val db_port = "3306" //порт, на котором напущена СУБД
    private val db_name = "clients" //название БД в mysql
    private val stmt: Statement //выражения, отправляемые СУБД

    init{
        serverSocket = ServerSocket(PORT)
        print("Логин пользовотеля от СУБД: ")
        val login = readLine() ?: "" //логин от СУБД
        print("Пароль от СУБД: ")
        val psw = readLine() ?: "" //пароль от СУБД
        val connectionProperties = Properties()
        connectionProperties["user"] = login
        connectionProperties["password"] = psw
        connectionProperties["serverTimezone"] = "UTC"
        try {
            //подключаемся к СУБД
            db = DriverManager.getConnection("jdbc:mysql://$host:$db_port/$db_name", connectionProperties)
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