package com.kfu.imim.utils

import java.util.*
import kotlin.collections.ArrayList

//Класс для создания заданий. Сюда можно затолкать всё, что будет характеризовать задание. На основе этого класса
//будем создавать экземпляры класса TaskInstance
class TaskInstanceCreateStructure(_descr: String, _tech: ArrayList<String>, _deadline: Date) {
    private var description: String; //описание задачи
    private val technologies: ArrayList<String> = arrayListOf();// список требуемых навыков
    private var prize : Int = 0;// премия за выполение в срок
    private val deadline: Date;
    private var status : Status;// статус задания
    // список исполнителей

    init{
        description = _descr;
        technologies.addAll(_tech);
        deadline = _deadline.clone() as Date;
        status = Status.WAITNIG;
    }

    constructor(_descr: String, _tech: ArrayList<String>, _deadline: Date, _prize: Int): this(_descr, _tech, _deadline){
        prize = _prize;
    }

}

enum class Status{
    COMPLETE, // задание завершено
    INWORK,// задание находится в работе
    WAITNIG//задание ожидает исполнителей
}