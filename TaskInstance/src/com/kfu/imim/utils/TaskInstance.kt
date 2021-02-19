package com.kfu.imim.utils


//Класс заданий. Тут будут создаваться все задания, их прогресс, текущие исполнители и прочая ерунда на ваш выбор
class TaskInstance(_taskInstanceCreateStructure: TaskInstanceCreateStructure) {
    private val taskInstanceCreateStructure: TaskInstanceCreateStructure;
    private val executors: ArrayList<Person> = arrayListOf();
    private var progress: Int = 0;

    init {
        taskInstanceCreateStructure = _taskInstanceCreateStructure.clone();
    }

    public fun addExecutor(value: Person){
        if(executors.size == 0){
            taskInstanceCreateStructure.setStatus(Status.INWORK);
        }
        executors.add(value);
    }

    public fun removeExecutor(value: Person){

    }
}

