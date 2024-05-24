package com.jamavcode.prog.service;

import java.util.List;

import com.jamavcode.prog.model.Subject;

public interface SubjectService {

    List<Subject> getAllSubjects();

    Subject getSubjectById(int subject_id);

    void insertSubject(String title, String description);

    void updateSubjectById(String title, String description, int subject_id);

    void deleteSubjectById(int subject_id);

}
