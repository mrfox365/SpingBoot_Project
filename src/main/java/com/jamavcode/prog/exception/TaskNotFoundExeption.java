package com.jamavcode.prog.exception;

public class TaskNotFoundExeption extends RuntimeException {

    private final int task_id;

    public TaskNotFoundExeption(int task_id){
        this.task_id = task_id;
    }

    @Override
    public String getMessage() {
        return "task with id = " + task_id + " not found";
    }

}
