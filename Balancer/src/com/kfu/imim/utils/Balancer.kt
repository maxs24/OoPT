package com.kfu.imim.utils
//Объект-балансироващик. Основная функция - вызывать функцию balance для каждой задачи.
//Отличие от оптимизатора в том, что он вызывается только на этапе создания задачи и работает на клиенте (но можно
//сделать и на серваке, в то время как оптимизатор работает на сервере и предлагает способы ускорить/улучшить
//выполнение задачи за счёт освободившихся людей

class Recommendations {

}


object Balancer {
    fun balance(taskInstance: TaskInstance) : Recommendations {
        //Эта функция принимает задачу и возвращает оптимальные рекомендации по времени/исполнителям/ресурсам
        //Вызывается из окна отрисовки клиента. Само собой этих исполнителей ещё надо получить
        return Recommendations()
    }

    fun balance (taskInstanceCreateStructure: TaskInstanceCreateStructure) : Recommendations {

        return Recommendations()
    }
}