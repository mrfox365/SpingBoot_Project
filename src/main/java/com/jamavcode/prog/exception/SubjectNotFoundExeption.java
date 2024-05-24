package com.jamavcode.prog.exception;

public class SubjectNotFoundExeption extends RuntimeException {

    private final int subject_id;

    public SubjectNotFoundExeption(int subject_id){
        this.subject_id = subject_id;
    }

    @Override
    public String getMessage() {
        return "Subject with id = " + subject_id + " not found";
    }

}
