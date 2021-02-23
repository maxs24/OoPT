package com.kfu.imim.components.layouts

import ClientInfo
import java.awt.Color
import java.awt.Container
import java.awt.FlowLayout
import java.awt.GridLayout
import java.util.*
import javax.swing.*
import com.kfu.imim.components.window
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.awt.Image

abstract class PersonalLayoutCreationStructure (open val clientInfo: ClientInfo, val contentPane: Container)

abstract class PersonalLayout(_personalLayoutCreationStructure: PersonalLayoutCreationStructure) {
    val personalLayoutCreationStructure = _personalLayoutCreationStructure

    open inner class TopLayout : JPanel(FlowLayout(), true) {

        val lblDate = JLabel(Calendar.getInstance().time.toString())
        open val btnTask = JButton("Задания")
        val btnReports = JButton("Отчёты")
        val btnLogout = JButton("Выход")
        init {
            background = Color.BLUE
            btnLogout.addActionListener { logout() }
        }

        private fun logout() {
            Client.getInstance().logout()
            window.contentPane.removeAll()
            window.layout = LoginLayout(personalLayoutCreationStructure.contentPane)
        }
    }

    open inner class PersonalInfoLayout :
            JPanel(GridLayout(0,1), true) {
        private val resizedPhoto = getScaledImage(
                ImageIcon(personalLayoutCreationStructure.clientInfo.photo).image,
                150,150
        )
        val photo = JLabel(ImageIcon(resizedPhoto))
        val lblSurname = JLabel(personalLayoutCreationStructure.clientInfo.surname)
        val lblName = JLabel(personalLayoutCreationStructure.clientInfo.name)
        val lblPatronymic = JLabel(personalLayoutCreationStructure.clientInfo.patronymic)
        init {
            background = Color.CYAN
        }

        private fun getScaledImage(srcImg: Image, w: Int, h: Int): Image {
            val resizedImg = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
            val g2 = resizedImg.createGraphics()
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
            g2.drawImage(srcImg, 0, 0, w, h, null)
            g2.dispose()
            return resizedImg
        }
    }

    open inner class TaskReviews : JPanel(GridLayout(1,1)) {

        init{
            background = Color.GREEN
        }
    }

    inner class RatingLayout : JPanel(GridLayout(1, 10)) {
        private val topWorkers = Array(10) { JLabel(it.toString()) }
        init {
            topWorkers.forEach { add(it) }
            background = Color.YELLOW
        }
    }

    open val topLayout = TopLayout()
    open val personalInfoLayout = PersonalInfoLayout()
    open val taskReviews = TaskReviews()
    private val ratingLayout = RatingLayout()
    fun createLayout () : GroupLayout {
        val gl = GroupLayout(personalLayoutCreationStructure.contentPane)
        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(4,10, Int.MAX_VALUE)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(topLayout)
                                        .addGroup(
                                                gl.createSequentialGroup()
                                                        .addComponent(personalInfoLayout, 180,200,250)
                                                        .addGap(4,4,4)
                                                        .addComponent(taskReviews, 200,225, Int.MAX_VALUE)
                                                        .addGap(4,4,4)
                                                        .addComponent(ratingLayout,75,75,125)
                                        )
                        )
                        .addGap(4,10, Int.MAX_VALUE)
        )
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGap(4,4, 4)
                        .addComponent(topLayout, 50,75,75)
                        .addGap(4,4,4)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(personalInfoLayout, 400, 450, Int.MAX_VALUE)
                                        .addComponent(taskReviews,400, 450, Int.MAX_VALUE)
                                        .addComponent(ratingLayout,400, 450, Int.MAX_VALUE)
                        )
                        .addGap(4,4,4)
        )

        return gl
    }
}