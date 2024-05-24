package com.jamavcode.prog.repository;

import com.jamavcode.prog.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository {

    List<Subject> getAllSubjects();

    Optional<Subject> getSubjectsById(int subject_id);

    void insertSubject(String title, String description);

    void updateSubjectById(String title, String description, int subject_id);

    void deleteSubjectById(int subject_id);

}