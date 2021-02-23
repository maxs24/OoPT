package com.kfu.imim.components.layouts

import ExecutorInfo
import java.awt.Color
import java.awt.Container
import javax.swing.*

class ExecutorLayoutCreationStructure(override val clientInfo: ExecutorInfo, contentPane: Container) :
        PersonalLayoutCreationStructure(clientInfo, contentPane)

class ExecutorLayout(private val executorLayoutCreationStructure: ExecutorLayoutCreationStructure) :
        PersonalLayout(executorLayoutCreationStructure){

    inner class ExecutorTopLayout : TopLayout() {

        override val btnTask = JButton("Взять задания")
        init {
            add(lblDate)
            add(btnTask)
            add(btnReports)
            add(btnLogout)
        }
    }

    inner class ExecutorPersonalInfo : PersonalInfoLayout() {
        private val lblKpi = JLabel("kpi: ${executorLayoutCreationStructure.clientInfo.kpi}")
        private val lblWorkh = JLabel("workh: ${executorLayoutCreationStructure.clientInfo.workh}")
        init {
            add(photo)
            add(photo)
            add(lblSurname)
            add(lblName)
            add(lblPatronymic)
            add(lblKpi)
            add(lblWorkh)
        }
    }

    inner class ExecutorTaskReviews : TaskReviews() {

        init{
            background = Color.BLACK
        }
    }

    override val topLayout = ExecutorTopLayout()
    override val personalInfoLayout = ExecutorPersonalInfo()
    override val taskReviews = ExecutorTaskReviews()
}