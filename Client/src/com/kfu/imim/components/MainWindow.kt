package com.kfu.imim.components


import Client
import CustomerInfo
import ExecutorInfo
import UserType
import com.kfu.imim.components.layouts.*
import com.kfu.imim.utils.TaskInstanceCreateStructure
import javax.swing.*
import java.awt.Dimension

val window = MainWindow()

//Здесь непосредственно вся отрисовка. ВСЕ ВЗАИМОДЕЙСТВИЯ ЧЕРЕЗ КЛАСС CLIENT, тут только рисование
class MainWindow(): JFrame("Оптимизация производственных задач") {
    //Нарисовать окно авторизации, после авторизации отрисовать разные окна в зависимости от
    // того заказчик или исполнитель авторизовался. Отрисовывать рекомендации по создаваемой задаче
    //Для исполнителей выдавать подсказки при создании и заполнении профиля

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        minimumSize = Dimension(800, 600)

        layout = LoginLayout(contentPane)
        isVisible = true

        pack()
    }

    fun chooseUserType(userType: UserType, id: String) {
        //TODO(Получить инфу по id)
        contentPane.removeAll()
        layout = when(userType) {
            UserType.Customer -> {
                val customerLayoutCreationStructure =
                        CustomerLayoutCreationStructure(Client.getInstance().clientInfo as CustomerInfo, contentPane)
                val customerLayout = CustomerLayout(customerLayoutCreationStructure)
                customerLayout.createLayout()
            }
            UserType.Executor -> {
                val executorLayoutCreationStructure =
                        ExecutorLayoutCreationStructure(Client.getInstance().clientInfo as ExecutorInfo, contentPane)
                val executorLayout = ExecutorLayout(executorLayoutCreationStructure)
                executorLayout.createLayout()
            }
            UserType.None -> {
                layout //TODO(Сделать регистрацию)
            }
        }
    }

    fun getRecommendations(taskInstanceCreateStructure: TaskInstanceCreateStructure) {
        Client.getInstance().getRecommendations(taskInstanceCreateStructure)
    }
}