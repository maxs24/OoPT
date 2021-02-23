package com.kfu.imim.utils


//Класс заданий. Тут будут создаваться все задания, их прогресс, текущие исполнители и прочая ерунда на ваш выбор
class TaskInstance(_taskInstanceCreateStructure: TaskInstanceCreateStructure, _subtasks: Array<SubTask>) {
    private val taskInstanceCreateStructure: TaskInstanceCreateStructure; //  само задание
    private val executors: ArrayList<Person> = arrayListOf();// список исполнителей
    private var progress: Int = 0;// прогресс
    private val subtasks: Array<SubTask>; // массив подзадач(нужно для прогресса)

    init {
        taskInstanceCreateStructure = _taskInstanceCreateStructure.clone()
        subtasks = _subtasks.copyOf()
    }

    public fun addExecutor(value: Person){
        if(executors.size == 0){
            taskInstanceCreateStructure.setStatus(Status.INWORK)
        }
        executors.add(value);
    }

    public fun completeSubTask(value: Int){
        subtasks[value].completeTask();
        progress += 100/subtasks.size;
        if(progress == 100){
            taskInstanceCreateStructure.setStatus(Status.COMPLETE);
        }
    }
}

