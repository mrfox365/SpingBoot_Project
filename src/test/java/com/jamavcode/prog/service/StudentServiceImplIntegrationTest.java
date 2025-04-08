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

/**
 * Тести для перевірки функціональності сервісу студентів.
 */
class StudentServiceImplIntegrationTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testStudent = new Student(1, "Dima", "Bereznuy", LocalDate.of(2004, 1, 1), "Group13");
    }

    /**
     * Example: Fetching a student by ID.
     * This test ensures that the service correctly fetches a student by their ID.
     * <p>
     * Usage example:
     * {@code
     *     StudentService studentService = new StudentServiceImpl();
     *     Student student = studentService.getStudentById(1);
     * }
     * Expected: The student with ID 1 is fetched successfully.
     */
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

    /**
     * Example: Handling a case when a student is not found by ID.
     * This test ensures that the service throws an exception when the student ID does not exist.
     * <p>
     * Usage example:
     * {@code
     *     try {
     *         studentService.getStudentById(1);
     *     } catch (StudentNotFoundException e) {
     *         // Handle exception
     *     }
     * }
     * Expected: A StudentNotFoundException should be thrown.
     */
    @Test
    void testGetStudentById_StudentNotFoundException() {
        // Arrange
        when(studentRepository.getStudentById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(1));
        verify(studentRepository, times(1)).getStudentById(1);
    }

    /**
     * Example: Inserting a new student into the database.
     * This test ensures the correct parameters are passed to the repository for student insertion.
     * <p>
     * Usage example:
     * {@code
     *     studentService.insertStudent("Dima", "Bereznuy", LocalDate.of(2004, 1, 1), "Group13");
     * }
     * Expected: The new student is successfully inserted into the repository.
     */
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

    /**
     * Example: Fetching students by subject ID.
     * This test ensures that students are fetched correctly for the given subject ID.
     * <p>
     * Usage example:
     * {@code
     *     List<Student> students = studentService.getStudentBySubjectId(101);
     * }
     * Expected: A list containing the student(s) enrolled in the subject with ID 101.
     */
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

    /**
     * Example: Updating a student's details by their ID.
     * This test ensures that the service correctly updates the student's information.
     * <p>
     * Usage example:
     * {@code
     *     studentService.updateStudentById("Miroslav", "Bahman", LocalDate.of(2003, 5, 15), "Group13", 1);
     * }
     * Expected: The student's information is updated in the database.
     */
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

    /**
     * Example: Deleting a student by their ID.
     * This test ensures that the service correctly deletes the student from the database.
     * <p>
     * Usage example:
     * {@code
     *     studentService.deleteStudentById(1, 101);
     * }
     * Expected: The student is deleted from the database.
     */
    @Test
    void testDeleteStudentById() {
        // Arrange
        when(studentRepository.getStudentById(1)).thenReturn(Optional.of(testStudent));

        // Act
        studentService.deleteStudentById(1, 101);

        // Assert
        verify(studentRepository, times(1)).deleteStudentById(1, 101);
    }

    /**
     * Example: Inserting a student along with the subject they are enrolled in.
     * This test ensures the student and subject are both inserted into the database.
     * <p>
     * Usage example:
     * {@code
     *     studentService.insertStudentAndSubject("Dima", "Bereznuy", LocalDate.of(2004, 1, 1), "Group13", 101);
     * }
     * Expected: Both the student and the subject are inserted correctly.
     */
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