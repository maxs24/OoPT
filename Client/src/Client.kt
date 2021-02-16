import com.kfu.imim.utils.Balancer
import com.kfu.imim.utils.Recommendations
import com.kfu.imim.utils.TaskInstance
import com.kfu.imim.utils.TaskInstanceCreateStructure

class Client private constructor() {
    companion object {
        private val client: Client = Client()

        fun getInstance(): Client {
            return client
        }
    }
    var recommendations: Recommendations? = null

    private fun submitTask() {
        //действие, вызываемое при нажатии кнопки "Отправить". Создаёт задачу с заданными в форме параметрами
        // само собой такая кнопка есть только у заказчиков
        val taskInstanceCreateStructure: TaskInstanceCreateStructure = TaskInstanceCreateStructure()
        //заполняем структуру данными из формы
        val taskInstance = TaskInstance(taskInstanceCreateStructure)
    }

    private fun getRecommendations() { //Получить рекомендации по текущей задаче. Обязательно вызвать хотя бы раз
        // при создании задачи, желательно вызывать после каждого обновления информации в форме. Ниже пример
        recommendations = Balancer.balance(TaskInstance(TaskInstanceCreateStructure()))
    }
}