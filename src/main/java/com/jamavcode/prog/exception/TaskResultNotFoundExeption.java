package com.jamavcode.prog.exception;

public class TaskResultNotFoundExeption extends RuntimeException {

    private final int taskResult_id;

    public TaskResultNotFoundExeption(int taskResult_id){
        this.taskResult_id = taskResult_id;
    }

    @Override
    public String getMessage() {
        return "taskResult with id = " + taskResult_id + " not found";
    }

}
