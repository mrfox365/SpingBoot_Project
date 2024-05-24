package com.jamavcode.prog.exception;

public class StudentNotFoundExeption extends RuntimeException {

    private final int student_id;

    public StudentNotFoundExeption(int student_id){
        this.student_id = student_id;
    }

    @Override
    public String getMessage() {
        return "student with id = " + student_id + " not found";
    }

}