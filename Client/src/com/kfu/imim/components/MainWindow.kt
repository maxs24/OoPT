package com.kfu.imim.components

import Client
import UserType
import com.kfu.imim.utils.TaskInstanceCreateStructure
import javax.swing.*
import java.awt.Dimension

val window = MainWindow()
//Здесь непосредственно вся отрисовка. ВСЕ ВЗАИМОДЕЙСТВИЯ ЧЕРЕЗ КЛАСС CLIENT, тут только рисование
class MainWindow: JFrame("Оптимизация производственных задач") {
    //Нарисовать окно авторизации, после авторизации отрисовать разные окна в зависимости от
    // того заказчик или исполнитель авторизовался. Отрисовывать рекомендации по создаваемой задаче
    //Для исполнителей выдавать подсказки при создании и заполнении профиля

    val taLogin = JTextField("Login")
    val taPassword = JPasswordField("password")
    val btnAccept = JButton("Войти")
    var client = Client.getInstance()

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        minimumSize = Dimension(800, 600)

        btnAccept.addActionListener {
            client.login(taLogin.text, taPassword.password.toString())
        }

        val gl = GroupLayout(contentPane)
        layout = gl
        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(4, 10, Int.MAX_VALUE)
                        .addGroup(
                                gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(
                                                taLogin,
                                                GroupLayout.Alignment.CENTER,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE,
                                                minimumSize.width/4)
                                        .addComponent(
                                                taPassword,
                                                GroupLayout.Alignment.CENTER,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE,
                                                minimumSize.width/4)
                                        .addComponent(
                                                btnAccept,
                                                GroupLayout.Alignment.CENTER,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE,
                                                minimumSize.width/4)
                        )
                        .addGap(4, 10, Int.MAX_VALUE)
        )

        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGap(4, 10, Int.MAX_VALUE)
                        .addGroup(
                                gl.createSequentialGroup()
                                        .addComponent(taLogin, 25, 30, 40)
                                        .addComponent(taPassword, 25, 30, 40)
                                        .addComponent(btnAccept, 25, 30, 40)
                        )
                        .addGap(4, 10, Int.MAX_VALUE)
        )
        pack()
        isVisible = true
    }

    fun chooseUserType(userType: UserType, name: String) {
        layout = when(userType) {
            UserType.Customer -> {
                createCustomerLayout(name)
            }
            UserType.Executor -> {
                createExecutorLayout(name)
            }
        }
    }

    private fun createCustomerLayout (name: String) : GroupLayout {
        val gl = GroupLayout(contentPane)
        //TODO(Сделать окно заказчика)
        pack()
        return gl
    }

    private fun createExecutorLayout (name: String) : GroupLayout {
        val gl = GroupLayout(contentPane)
        //TODO(Сделать окно исполнителя)
        pack()
        return gl
    }

    fun getRecommendations(taskInstanceCreateStructure: TaskInstanceCreateStructure) {
        client.getRecommendations(taskInstanceCreateStructure)
    }
}