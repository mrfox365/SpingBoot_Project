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
            "John", 
            "Doe", 
            LocalDate.of(2000, 1, 1), 
            "GroupA"
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
        studentService.insertStudent("John", "Doe", LocalDate.of(2000, 1, 1), "GroupA");

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

        assertEquals("John", firstNameCaptor.getValue());
        assertEquals("Doe", lastNameCaptor.getValue());
        assertEquals(LocalDate.of(2000, 1, 1), dobCaptor.getValue());
        assertEquals("GroupA", groupNameCaptor.getValue());
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
        studentService.updateStudentById("Jane", "Smith", LocalDate.of(1999, 5, 15), "GroupB", 1);

        // Assert
        verify(studentRepository, times(1)).updateStudentById(
            "Jane", "Smith", LocalDate.of(1999, 5, 15), "GroupB", 1
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
        studentService.insertStudentAndSubject("John", "Doe", LocalDate.of(2000, 1, 1), "GroupA", 101);

        // Assert
        verify(studentRepository, times(1)).insertStudentAndSubject(
            "John", "Doe", LocalDate.of(2000, 1, 1), "GroupA", 101
        );
    }
}