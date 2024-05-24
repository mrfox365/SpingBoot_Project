package com.jamavcode.prog.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jamavcode.prog.exception.StudentNotFoundExeption;
import com.jamavcode.prog.model.Student;
import com.jamavcode.prog.repository.StudentRepository;

@Primary
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudentBySubjectId(int subject_id){
        return studentRepository.getStudentBySubjectId(subject_id);
    }

    @Override
    public List<Student> getStudentBySubjectIdReverse(int subject_id){
        return studentRepository.getStudentBySubjectIdReverse(subject_id);
    }

    @Override
    public Student getStudentById(int student_id){
        return studentRepository.getStudentById(student_id)
            .orElseThrow(() -> new StudentNotFoundExeption(student_id));
    }

    @Override
    public void insertStudent(String first_name, String last_name, LocalDate date_of_birth, String group_name){
        studentRepository.insertStudent(first_name, last_name, date_of_birth, group_name);
    }
    
    @Override
    public void insertStudentAndSubject(String first_name, String last_name, LocalDate date_of_birth, String group_name, int subject_id){
        studentRepository.insertStudentAndSubject(first_name, last_name, date_of_birth, group_name, subject_id);
    }

    @Override
    public void insertStudentSubject(int student_id, int subject_id){
        studentRepository.insertStudentSubject(student_id, subject_id);
    }

    @Override
    public void updateStudentById(String first_name, String last_name, LocalDate date_of_birth, String group_name, int student_id){
        var student = studentRepository.getStudentById(student_id)
            .orElseThrow(() -> new StudentNotFoundExeption(student_id));
        studentRepository.updateStudentById(first_name, last_name, date_of_birth, group_name, student.student_id());
    }

    @Override
    public void deleteStudentById(int student_id, int subject_id){
        var student = studentRepository.getStudentById(student_id)
            .orElseThrow(() -> new StudentNotFoundExeption(student_id));
        studentRepository.deleteStudentById(student.student_id(), subject_id);
    }

}
