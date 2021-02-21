package com.kfu.imim.utils

class SubTask(_sub_description: String) {
    private val sub_description: String;
    private var iscomplete: Boolean;

    init{
        sub_description = _sub_description;
        iscomplete = false;
    }

    public fun getSubDescription(): String{
        return sub_description;
    }

    public fun isComplete(): Boolean{
        return iscomplete;
    }

    public fun completeTask(){
        iscomplete = true;
    }

}