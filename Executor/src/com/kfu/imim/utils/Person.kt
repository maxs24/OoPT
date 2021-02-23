package com.kfu.imim.utils



class Person(_fullname: String, _freehours: Int) {
    private val fullname: String; //ФИО исполнителя
    private val technologies: ArrayList<Tech> = arrayListOf();//список освоенных технологий
    private var freehours: Int;// количество свободных часов в неделю
    private val currentTasks: ArrayList<TaskInstance> = arrayListOf();// список взятых задач

    init{
        fullname = _fullname;
        freehours = _freehours;
    }

    constructor(_fullname: String, _freehours: Int, _technologies: ArrayList<Tech>): this(_fullname,_freehours){
        technologies.addAll(_technologies);
    }

    public fun addTechnology(value: Tech){
        technologies.add(value);
    }

    public fun addCurrentTask(task: TaskInstance){
        currentTasks.add(task);
    }

    public fun removeCurrentTask(task: TaskInstance){
        currentTasks.remove(task);
    }

    public fun setFreeHours(value:Int): Boolean{
        if(value <= 168){
            freehours = value;
            return true;
        }
        return false;
    }

    public fun getFreeHours():Int{
        return freehours;
    }

    public fun getTechnologies(): ArrayList<Tech>{
        val new_technologies: ArrayList<Tech> = arrayListOf();
        new_technologies.addAll(technologies);
        return new_technologies;
    }

    public fun getCurrentTasks(): ArrayList<TaskInstance>{
        val new_currenttasks = arrayListOf<TaskInstance>();
        new_currenttasks.addAll(currentTasks);
        return new_currenttasks;
    }

}