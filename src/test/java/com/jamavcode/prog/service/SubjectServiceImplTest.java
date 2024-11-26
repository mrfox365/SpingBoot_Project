package com.jamavcode.prog.service;

import com.jamavcode.prog.exception.SubjectNotFoundException;
import com.jamavcode.prog.model.Subject;
import com.jamavcode.prog.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSubjects_shouldReturnListOfSubjects() {
        // Arrange
        List<Subject> mockSubjects = List.of(
            new Subject(1, "Math", "Mathematics Subject"),
            new Subject(2, "Physics", "Physics Subject")
        );
        when(subjectRepository.getAllSubjects()).thenReturn(mockSubjects);

        // Act
        List<Subject> result = subjectService.getAllSubjects();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).title());
        verify(subjectRepository, times(1)).getAllSubjects();
    }

    @Test
    void getSubjectById_shouldReturnSubject_whenSubjectExists() {
        // Arrange
        int subjectId = 1;
        Subject mockSubject = new Subject(subjectId, "Math", "Mathematics Subject");
        when(subjectRepository.getSubjectsById(subjectId)).thenReturn(Optional.of(mockSubject));

        // Act
        Subject result = subjectService.getSubjectById(subjectId);

        // Assert
        assertNotNull(result);
        assertEquals("Math", result.title());
        verify(subjectRepository, times(1)).getSubjectsById(subjectId);
    }

    @Test
    void getSubjectById_shouldThrowException_whenSubjectDoesNotExist() {
        // Arrange
        int subjectId = 99;
        when(subjectRepository.getSubjectsById(subjectId)).thenReturn(Optional.empty());

        // Act & Assert
        SubjectNotFoundException exception = assertThrows(SubjectNotFoundException.class, () -> {
            subjectService.getSubjectById(subjectId);
        });
        assertEquals("Subject with id = 99 not found", exception.getMessage());
        verify(subjectRepository, times(1)).getSubjectsById(subjectId);
    }

    @Test
    void insertSubject_shouldCallRepository() {
        // Arrange
        String title = "Biology";
        String description = "Biology Subject";

        // Act
        subjectService.insertSubject(title, description);

        // Assert
        verify(subjectRepository, times(1)).insertSubject(title, description);
    }

    @Test
    void updateSubjectById_shouldUpdateSubject_whenSubjectExists() {
        // Arrange
        int subjectId = 1;
        String newTitle = "Advanced Math";
        String newDescription = "Updated Description";
        Subject mockSubject = new Subject(subjectId, "Math", "Old Description");
        when(subjectRepository.getSubjectsById(subjectId)).thenReturn(Optional.of(mockSubject));

        // Act
        subjectService.updateSubjectById(newTitle, newDescription, subjectId);

        // Assert
        verify(subjectRepository, times(1)).updateSubjectById(newTitle, newDescription, subjectId);
    }

    @Test
    void deleteSubjectById_shouldDeleteSubject_whenSubjectExists() {
        // Arrange
        int subjectId = 1;
        Subject mockSubject = new Subject(subjectId, "Math", "Mathematics Subject");
        when(subjectRepository.getSubjectsById(subjectId)).thenReturn(Optional.of(mockSubject));

        // Act
        subjectService.deleteSubjectById(subjectId);

        // Assert
        verify(subjectRepository, times(1)).deleteSubjectById(subjectId);
    }
}
