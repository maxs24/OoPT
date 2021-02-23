package com.kfu.imim.utils

import java.util.*

//Оптимизатор задач. Работает на сервере, предлагает новых людей, новые ресурсы если задача не выполняется в срок.
class Optimizer {

    public fun isDeadline(taskinstance: TaskInstance, allexecutors: ArrayList<Person>): ArrayList<Person>?{ // если у задания прошло пол срока и работа не сдвинулась возвращает массив подходящих исполнителей
        val task = taskinstance.getTaskInstanceSturcture();
        if(taskinstance.getProgress() <= 25 && task.timeLeft() <= (task.getDeadline().day - task.getDateCreation().day)/2){
            return suitableExecutors(task,allexecutors)
        }else{
            return null;
        }
    }

    public fun isNewTech(executor: Person, alltasks: ArrayList<TaskInstance>, new_tech: Tech): ArrayList<TaskInstance>{ // при добавлении новой технологии исполнителю возвращает массив подходящих задач
        return suitableTasks(alltasks,executor,new_tech);
    }

    private fun suitableExecutors(taskinstance: TaskInstanceCreateStructure,executors: ArrayList<Person>): ArrayList<Person>{
        val exec: ArrayList<Person> = ArrayList();
        for(exe in executors){
            val exe_tech: HashSet<Tech> = HashSet(exe.getTechnologies());
            if(exe_tech.containsAll(taskinstance.getTechnologies())){
                exec.add(exe);
            }
        }
        return exec;
    }

    private fun suitableTasks(tasks: ArrayList<TaskInstance>, executor: Person, new_tech: Tech): ArrayList<TaskInstance>{
        val suittasks: ArrayList<TaskInstance> = ArrayList();
        val exe_tech: HashSet<Tech> = HashSet(executor.getTechnologies());
        for(task in tasks){
            val task_tech: HashSet<Tech> = HashSet(task.getTaskInstanceSturcture().getTechnologies());
            if(task_tech.contains(new_tech) && exe_tech.containsAll(task_tech)){
                suittasks.add(task);
            }
        }
        return suittasks;
    }
}