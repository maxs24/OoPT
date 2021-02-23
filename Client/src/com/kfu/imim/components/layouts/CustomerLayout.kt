package com.kfu.imim.components.layouts

import CustomerInfo
import java.awt.*
import javax.swing.*

class CustomerLayoutCreationStructure (override val clientInfo: CustomerInfo, contentPane: Container) :
        PersonalLayoutCreationStructure(clientInfo, contentPane)

class CustomerLayout(customerLayoutCreationStructure: CustomerLayoutCreationStructure) :
        PersonalLayout(customerLayoutCreationStructure) {

    inner class CustomerTopLayout : TopLayout() {

        override val btnTask = JButton("Руководить")
        init {
            add(lblDate)
            add(btnTask)
            add(btnReports)
            add(btnLogout)
        }
    }

    inner class CustomerPersonalInfo : PersonalInfoLayout() {
        private val lblPosition = JLabel("position: Director")
        init {
            add(photo)
            add(lblSurname)
            add(lblName)
            add(lblPatronymic)
            add(lblPosition)
        }
    }

    inner class CustomerTaskReviews : TaskReviews() {

        init{
            background = Color.WHITE
        }
    }

    override val topLayout = CustomerTopLayout()
    override val personalInfoLayout = CustomerPersonalInfo()
    override val taskReviews = CustomerTaskReviews()
}