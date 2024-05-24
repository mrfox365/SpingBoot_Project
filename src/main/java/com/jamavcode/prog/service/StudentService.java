package com.jamavcode.prog.service;

import java.time.LocalDate;
import java.util.List;

import com.jamavcode.prog.model.Student;

public interface StudentService {

    List<Student> getStudentBySubjectId(int subject_id);
    
    List<Student> getStudentBySubjectIdReverse(int subject_id);

    Student getStudentById(int student_id);

    void insertStudent(String first_name, String last_name, LocalDate date_of_birth, String group_name);

    void insertStudentAndSubject(String first_name, String last_name, LocalDate date_of_birth, String group_name, int subject_id);

    void insertStudentSubject(int student_id, int subject_id);

    void updateStudentById(String first_name, String last_name, LocalDate date_of_birth, String group_name, int student_id);

    void deleteStudentById(int student_id, int subject_id);

}
