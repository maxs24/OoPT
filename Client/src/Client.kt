import com.kfu.imim.components.window
import com.kfu.imim.networking.Communicator
import com.kfu.imim.utils.Balancer
import com.kfu.imim.utils.Recommendations
import com.kfu.imim.utils.TaskInstance
import com.kfu.imim.utils.TaskInstanceCreateStructure
import java.net.Socket

enum class UserType{
    Customer,
    Executor
}

class Client private constructor() {
    companion object {
        private val client: Client = Client()

        fun getInstance(): Client {
            return client
        }
    }
    val serverAdress = "localhost"
    val port = 5703
    //val socket = Socket(serverAdress, port)
    var recommendations: Recommendations? = null
    //val communicator = Communicator(socket)

    init {
        //communicator.addDataRecievedListener(::dataReceived)
    }

    private fun dataReceived (data: String) {
        val vls = data.split("=", limit = 2)
        if (vls.size == 2){
            when (vls[0]){ //when - это как switch на с++
                "login" -> loginResponse(vls[1])
            }
        }
    }

    fun loginResponse(string: String) {
        val vls = string.split(":")
        try{
            when(vls[0]){
                "ok" -> { window.chooseUserType(UserType.valueOf(vls[1]), vls[2])}
                else -> { }
            }
        }
        catch (ex: IllegalArgumentException) {
            println("Не сработал valueOf $ex")
        }

    }

    fun submitTask() {
        //действие, вызываемое при нажатии кнопки "Отправить". Создаёт задачу с заданными в форме параметрами
        // само собой такая кнопка есть только у заказчиков
        val taskInstanceCreateStructure: TaskInstanceCreateStructure = TaskInstanceCreateStructure()
        //заполняем структуру данными из формы
        val taskInstance = TaskInstance(taskInstanceCreateStructure)
    }

    fun getRecommendations(taskInstanceCreateStructure: TaskInstanceCreateStructure) { //Получить рекомендации по текущей задаче. Обязательно вызвать хотя бы раз
        // при создании задачи, желательно вызывать после каждого обновления информации в форме. Ниже пример
        recommendations = Balancer.balance(taskInstanceCreateStructure)
        showRecommendations(recommendations)
    }

    fun showRecommendations(recommendations: Recommendations?) {
        if(recommendations == null) return

        //TODO(Показать рекомендации)
    }

    fun login (login: String, password: String) {
        //communicator.sendData("login=$login:$password")
    }
}