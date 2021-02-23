package com.kfu.imim.utils

import java.util.*
import kotlin.collections.ArrayList

//Класс для создания заданий. Сюда можно затолкать всё, что будет характеризовать задание. На основе этого класса
//будем создавать экземпляры класса TaskInstance
class TaskInstanceCreateStructure(_descr: String, _tech: ArrayList<Skills>, _deadline: Date,_payment:Int) {
    private var description: String; //описание задачи
    private val technologies: ArrayList<Skills> = arrayListOf();// список требуемых навыков
    private var prize : Int = 0;// премия за выполение в срок
    private val deadline: Date
    private var status : Status// статус задания
    private var payment: Int // оплата за задание



    init{
        description = _descr;
        technologies.addAll(_tech);
        deadline = _deadline.clone() as Date;
        status = Status.WAITING;
        payment = _payment;

    }

    constructor(_descr: String, _tech: ArrayList<Skills>, _deadline: Date, _prize: Int, _payment:Int): this(_descr, _tech, _deadline, _payment){
        prize = _prize;
    }

    public fun getDescription(): String{
        return description;
    }

    public fun getTechnologies(): ArrayList<Skills>{
        val new_technologies: ArrayList<Skills> = arrayListOf();
        new_technologies.addAll(technologies);
        return new_technologies;
    }

    public fun getPrize(): Int{
        return prize;
    }

    public fun getDeadline(): Date{
        return deadline.clone() as Date;
    }

    public fun getPayment(): Int{
        return payment;
    }

    public fun getStatus(): Status{
        return status;
    }

    public fun setStatus(value: Status){
        status = value;
    }

    public fun clone(): TaskInstanceCreateStructure{
        val new_object = TaskInstanceCreateStructure(this.getDescription(),this.getTechnologies(),this.getDeadline(),this.getPayment(),this.getPrize());
        return new_object;
    }

}

enum class Status{
    COMPLETE, // задание завершено
    INWORK,// задание находится в работе
    WAITING//задание ожидает исполнителей
}

enum class Skills{
    HTML,
    CSS,
    js,
    react,
    vuejs,
    angular,
    laravel,
    git,
    linux,
    c89,
    cpp,
    python,
    django,
    java,
    spring,
    kotlin,
    go,
    csharp,
    php,
    swift,
    sql
}