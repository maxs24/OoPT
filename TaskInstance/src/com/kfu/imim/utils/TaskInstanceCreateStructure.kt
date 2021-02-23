package com.kfu.imim.utils

import java.util.*
import kotlin.collections.ArrayList

//Класс для создания заданий. Сюда можно затолкать всё, что будет характеризовать задание. На основе этого класса
//будем создавать экземпляры класса TaskInstance
class TaskInstanceCreateStructure(_descr: String, _tech: ArrayList<Tech>, _deadline: Date) {
    private var description: String; //описание задачи
    private val technologies: ArrayList<Tech> = arrayListOf();// список требуемых навыков
    private var prize : Int = 0;// премия за выполение в срок
    private val deadline: Date;
    private var status : Status;// статус задания
    private var datecreation: Date;



    init{
        description = _descr;
        technologies.addAll(_tech);
        deadline = _deadline.clone() as Date;
        status = Status.WAITING;
        val calendar: Calendar = Calendar.getInstance();
        datecreation = Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
    }
    constructor(_descr: String, _tech: ArrayList<Tech>, _deadline: Date, _prize: Int): this(_descr, _tech, _deadline){
        prize = _prize;
    }

    constructor(_descr: String, _tech: ArrayList<Tech>, _deadline: Date, _prize: Int, _datecreation: Date): this(_descr, _tech, _deadline,_prize){
        datecreation = _datecreation;
    }

    public fun getDescription(): String{
        return description;
    }
    public fun getTechnologies(): ArrayList<Tech>{
        val new_technologies: ArrayList<Tech> = arrayListOf();
        new_technologies.addAll(technologies);
        return new_technologies;
    }

    public fun getPrize(): Int{
        return prize;
    }

    public fun getDeadline(): Date{
        return deadline.clone() as Date;
    }

    public fun getStatus(): Status{
        return status;
    }

    public fun setStatus(value: Status){
        status = value;
    }

    public fun getDateCreation(): Date{
        return datecreation;
    }

    public fun clone(): TaskInstanceCreateStructure{
        val new_object = TaskInstanceCreateStructure(this.getDescription(),this.getTechnologies(),this.getDeadline(),this.getPrize(),this.getDateCreation());
        return new_object;
    }

    public fun timeLeft(): Int{
        val calendar: Calendar = Calendar.getInstance();
        val date = Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        return deadline.day - date.day;
    }

}

enum class Status{
    COMPLETE, // задание завершено
    INWORK,// задание находится в работе
    WAITING//задание ожидает исполнителей
}

enum class Tech{
    HTML,
    CSS,
    JS,
    REACT,
    VUE_JS,
    ANGULAR,
    LARAVEL,
    GIT,
    LINUX,
    C89,
    Cpp,
    PYTHON,
    DJANGO,
    JAVA,
    SPRING,
    KOTLIN,
    GO,
    Csh,
    PHP,
    SWIFT,
    SQL,
    RUDY,
    Machine_Learning,
    ASP_NET,
    OneC
}
