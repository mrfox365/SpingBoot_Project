package com.jamavcode.prog.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jamavcode.prog.exception.SubjectNotFoundExeption;
import com.jamavcode.prog.model.Subject;
import com.jamavcode.prog.repository.SubjectRepository;

@Primary
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> getAllSubjects(){
        return subjectRepository.getAllSubjects();
    }

    @Override
    public Subject getSubjectById(int subject_id){
        return subjectRepository.getSubjectsById(subject_id)
            .orElseThrow(() -> new SubjectNotFoundExeption(subject_id));
    }

    @Override 
    public void insertSubject(String title, String description){
        subjectRepository.insertSubject(title, description);
    }

    @Override
    public void updateSubjectById(String title, String description, int subject_id){
        var subject = subjectRepository.getSubjectsById(subject_id)
            .orElseThrow(() -> new SubjectNotFoundExeption(subject_id));
        subjectRepository.updateSubjectById(title, description, subject.subject_id());
    }

    @Override
    public void deleteSubjectById(int subject_id){
        var subject = subjectRepository.getSubjectsById(subject_id)
            .orElseThrow(() -> new SubjectNotFoundExeption(subject_id));
        subjectRepository.deleteSubjectById(subject.subject_id());
    }

}
