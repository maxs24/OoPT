import com.kfu.imim.components.window
import com.kfu.imim.networking.Communicator
import com.kfu.imim.utils.*
import java.awt.Image
import java.io.File
import java.net.Socket
import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import javax.imageio.ImageIO
import javax.swing.ImageIcon

const val executorPassword = "mWoPq3VfBXDJj3p5"
const val customerPassword = "4g48ZkexOvagONHt"
const val host = "localhost"
const val dbPort = 3306
const val dbName = "oopt"
val stdImage = File("./resources/camera_200.png").readBytes()

enum class UserType{
    None,
    Customer,
    Executor
}
abstract class ClientInfo(val ID: Int,
                      val surname: String,
                      val name: String,
                      val patronymic: String,
                      val photo : ByteArray)

class ExecutorInfo(ID: Int,
                   surname: String,
                   name: String,
                   patronymic: String,
                   photo : ByteArray,
                   val workh: Int,
                   val kpi: Float) : ClientInfo (ID, surname, name, patronymic, photo)

class CustomerInfo(ID: Int,
                   surname: String,
                   name: String,
                   patronymic: String,
                   photo: ByteArray) : ClientInfo(ID, surname, name, patronymic, photo)

class Client private constructor() {
    companion object {
        private val client: Client = Client()

        fun getInstance(): Client {
            return client
        }
    }
    private val serverAddress = "localhost" //TODO(из командной строки)
    private val port = 5703
    private val socket = Socket(serverAddress, port)
    var recommendations: Recommendations? = null
    private val communicator = Communicator(socket)
    lateinit var clientInfo: ClientInfo

    init {
        communicator.addDataReceivedListener(::dataReceived)
        communicator.start()
    }

    private fun dataReceived (data: String) {
        println(data)
        val vls = data.split("=", limit = 2)
        if (vls.size == 2){
            when (vls[0]){ //when - это как switch на с++
                "login" -> loginResponse(vls[1])
            }
        }
    }

    private fun loginResponse(string: String) {
        val vls = string.split(":")
        try{
            when(vls[0]){
                "ok" -> {
                    getInfo(vls[2], UserType.valueOf(vls[1]));
                    window.chooseUserType(UserType.valueOf(vls[1]), vls[2])
                }
                else -> { } //TODO(Сообщение, что логин/пароль неверный)
            }
        }
        catch (ex: IllegalArgumentException) {
            println("Не сработал valueOf $ex")
        }

    }

    fun submitTask(taskInstanceCreateStructure: TaskInstanceCreateStructure, subTask: Array<SubTask>) {
        //действие, вызываемое при нажатии кнопки "Отправить". Создаёт задачу с заданными в форме параметрами
        // само собой такая кнопка есть только у заказчиков
        //TODO(Проверить корректность)
        val taskInstance = TaskInstance(taskInstanceCreateStructure, subTask)
    }

    fun getRecommendations(taskInstanceCreateStructure: TaskInstanceCreateStructure) { //Получить рекомендации по текущей задаче. Обязательно вызвать хотя бы раз
        // при создании задачи, желательно вызывать после каждого обновления информации в форме. Ниже пример
        recommendations = Balancer.balance(taskInstanceCreateStructure)
        showRecommendations(recommendations)
    }

    private fun getInfo(id: String, userType: UserType) {
        val db = createDBConnection(userType)
        val stmt = db?.createStatement()
        val rs = when(userType) {
            UserType.Executor -> stmt?.executeQuery(
                    "SELECT ID, surname, name, patronymic, photo, workh, kpi FROM exec_info WHERE ID = '$id'")
            UserType.Customer -> stmt?.executeQuery(
                    "SELECT ID, surname, name, patronymic, photo FROM cust_info WHERE ID = '$id'")
            else -> return
        }
        if(rs?.next() == true) {
            clientInfo = when (userType){
                UserType.Customer -> { CustomerInfo(
                        rs.getInt("ID"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("patronymic"),
                        rs.getBytes("photo")  ?: stdImage) }
                UserType.Executor -> { ExecutorInfo(
                        rs.getInt("ID"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("patronymic"),
                        rs.getBytes("photo") ?: stdImage,
                        rs.getInt("workh"),
                        rs.getFloat("kpi")
                )}
                else -> { return }
            }
        }
    }

    private fun createDBConnection(userType: UserType) : Connection? {
        val connectionProperties = Properties()
        when(userType) {
            UserType.Customer -> { connectionProperties["user"] = "customer"
                connectionProperties["password"] = customerPassword }
            UserType.Executor -> {connectionProperties["user"] = "executor"
                connectionProperties["password"] = executorPassword }
            UserType.None -> { } //TODO(Регистрация)
        }
        connectionProperties["serverTimezone"] = "UTC"
        connectionProperties["autoReconnect"] = true
        val db : Connection
        try {
            db = DriverManager.getConnection("jdbc:mysql://$host:$dbPort/$dbName", connectionProperties)
        }
        catch (ex: Exception) {
            //ex.printStackTrace()
            println("Error creating DB connection")
            println("Не найдена база данных. Дальнейшная работа невозможна.")
            return null
        }
        println("DB connection created successfully")
        return db
    }

    private fun showRecommendations(recommendations: Recommendations?) {
        if(recommendations == null) return

        //TODO(Показать рекомендации)
    }

    fun login (login: String, password: String) {
        communicator.sendData("login=$login:$password")
    }

    fun logout() {
        communicator.sendData("logout=${clientInfo.ID}")
    }
}