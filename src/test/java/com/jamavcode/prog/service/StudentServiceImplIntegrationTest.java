package com.jamavcode.prog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jamavcode.prog.exception.StudentNotFoundException;
import com.jamavcode.prog.model.Student;
import com.jamavcode.prog.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class StudentServiceImplIntegrationTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testStudent = new Student(
            1, 
            "Dima", 
            "Bereznuy", 
            LocalDate.of(2004, 1, 1), 
            "Group13"
        );
    }

    @Test
    void testGetStudentById_Success() {
        // Arrange
        when(studentRepository.getStudentById(1)).thenReturn(Optional.of(testStudent));

        // Act
        Student result = studentService.getStudentById(1);

        // Assert
        assertNotNull(result);
        assertEquals(testStudent, result);
        verify(studentRepository, times(1)).getStudentById(1);
    }

    @Test
    void testGetStudentById_StudentNotFoundException() {
        // Arrange
        when(studentRepository.getStudentById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(1));
        verify(studentRepository, times(1)).getStudentById(1);
    }

    @Test
    void testInsertStudent() {
        // Act
        studentService.insertStudent("Dima", "Bereznuy", LocalDate.of(2004, 1, 1), "Group13");

        // Assert
        ArgumentCaptor<String> firstNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> lastNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<LocalDate> dobCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<String> groupNameCaptor = ArgumentCaptor.forClass(String.class);

        verify(studentRepository, times(1)).insertStudent(
            firstNameCaptor.capture(),
            lastNameCaptor.capture(),
            dobCaptor.capture(),
            groupNameCaptor.capture()
        );

        assertEquals("Dima", firstNameCaptor.getValue());
        assertEquals("Bereznuy", lastNameCaptor.getValue());
        assertEquals(LocalDate.of(2004, 1, 1), dobCaptor.getValue());
        assertEquals("Group13", groupNameCaptor.getValue());
    }

    @Test
    void testGetStudentBySubjectId() {
        // Arrange
        when(studentRepository.getStudentBySubjectId(101)).thenReturn(List.of(testStudent));

        // Act
        List<Student> result = studentService.getStudentBySubjectId(101);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testStudent, result.get(0));
        verify(studentRepository, times(1)).getStudentBySubjectId(101);
    }

    @Test
    void testUpdateStudentById() {
        // Arrange
        when(studentRepository.getStudentById(1)).thenReturn(Optional.of(testStudent));

        // Act
        studentService.updateStudentById("Miroslav", "Bahman", LocalDate.of(2003, 5, 15), "Group13", 1);

        // Assert
        verify(studentRepository, times(1)).updateStudentById(
            "Miroslav", "Bahman", LocalDate.of(2003, 5, 15), "Group13", 1
        );
    }

    @Test
    void testDeleteStudentById() {
        // Arrange
        when(studentRepository.getStudentById(1)).thenReturn(Optional.of(testStudent));

        // Act
        studentService.deleteStudentById(1, 101);

        // Assert
        verify(studentRepository, times(1)).deleteStudentById(1, 101);
    }

    @Test
    void testInsertStudentAndSubject() {
        // Act
        studentService.insertStudentAndSubject("Dima", "Bereznuy", LocalDate.of(2004, 1, 1), "Group13", 101);

        // Assert
        verify(studentRepository, times(1)).insertStudentAndSubject(
            "Dima", "Bereznuy", LocalDate.of(2004, 1, 1), "Group13", 101
        );
    }
}