package com.kfu.imim.components.layouts

import java.awt.Container
import javax.swing.*

class LoginLayout(contentPane: Container) : GroupLayout(contentPane){
    private val taLogin = JTextField("Login")
    private val taPassword = JPasswordField("")
    private val btnAccept = JButton("Войти")

    init {
        btnAccept.addActionListener {
            Client.getInstance().login(taLogin.text, taPassword.password.joinToString(separator = ""))
        }

        this.setHorizontalGroup(
                this.createSequentialGroup()
                        .addGap(4, 10, Int.MAX_VALUE)
                        .addGroup(
                                this.createParallelGroup(Alignment.CENTER)
                                        .addComponent(
                                                taLogin,
                                                Alignment.CENTER,
                                                DEFAULT_SIZE,
                                                PREFERRED_SIZE,
                                                150)
                                        .addComponent(
                                                taPassword,
                                                Alignment.CENTER,
                                                DEFAULT_SIZE,
                                                PREFERRED_SIZE,
                                                150)
                                        .addComponent(
                                                btnAccept,
                                                Alignment.CENTER,
                                                DEFAULT_SIZE,
                                                PREFERRED_SIZE,
                                                50)
                        )
                        .addGap(4, 10, Int.MAX_VALUE)
        )

        this.setVerticalGroup(
                this.createSequentialGroup()
                        .addGap(4, 10, Int.MAX_VALUE)
                        .addGroup(
                                this.createSequentialGroup()
                                        .addComponent(taLogin, 25, 30, 40)
                                        .addComponent(taPassword, 25, 30, 40)
                                        .addComponent(btnAccept, 25, 30, 40)
                        )
                        .addGap(4, 10, Int.MAX_VALUE)
        )
    }
}