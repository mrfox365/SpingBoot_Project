package com.jamavcode.prog.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jamavcode.prog.model.Subject;
import com.jamavcode.prog.model.SubjectRequest;
import com.jamavcode.prog.model.Student;
import com.jamavcode.prog.model.StudentRequest;
import com.jamavcode.prog.model.Task;
import com.jamavcode.prog.model.TaskRequestInsert;
import com.jamavcode.prog.model.TaskRequestUpdate;
import com.jamavcode.prog.model.TaskResult;
import com.jamavcode.prog.model.TaskResultRequest;
import com.jamavcode.prog.service.SubjectService;
import com.jamavcode.prog.service.StudentService;
import com.jamavcode.prog.service.TaskService;

import jakarta.validation.Valid;

import com.jamavcode.prog.service.TaskResultService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ProgController {

    private final SubjectService subjectService;
    private final StudentService studentService;
    private final TaskService taskService;
    private final TaskResultService taskResultService;

    public ProgController(
        SubjectService subjectService,
        StudentService studentService,
        TaskService taskService,
        TaskResultService taskResultService
        ){
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.taskService = taskService;
        this.taskResultService = taskResultService;
    }

    private void loadCommonAttributes(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("pageTitle", "Керування навчанням");
        model.addAttribute("teacherName", "Викладач: Колесніков Валерій");
        model.addAttribute("subjects", subjects);
    }

    @GetMapping("/")
    public String index(Model model) {
        loadCommonAttributes(model);
        return "index";
    }
    
    @GetMapping(value = "/subjects/{subject_id:\\d+}")
    public String indexWithSubject(Model model, @PathVariable int subject_id) {
        loadCommonAttributes(model);
        List<Student> students = studentService.getStudentBySubjectId(subject_id);
        List<Student> studentsReverse = studentService.getStudentBySubjectIdReverse(subject_id);
        List<Task> tasks = taskService.getTasksBySubjectId(subject_id);
        List<TaskResult> taskResults = taskResultService.getTaskResultBySubjectId(subject_id);
        model.addAttribute("needSubject", subjectService.getSubjectById(subject_id));
        model.addAttribute("students", students);
        model.addAttribute("studentsReverse", studentsReverse);
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskResults", taskResults);
        return "index";
    }

    @PostMapping("/insertSubject")
    @ResponseBody
    public ResponseEntity<String> addSubject(@Valid SubjectRequest subjectRequest) {
        try {
            subjectService.insertSubject(subjectRequest.title(), subjectRequest.description());
            return ResponseEntity.ok("Subject added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add subject: " + e.getMessage());
        }
    }

    @PutMapping("/updateSubject/{subject_id}")
    @ResponseBody
    public ResponseEntity<String> updateSubject(@PathVariable("subject_id") int subject_id, @RequestBody @Valid SubjectRequest subjectRequest) {
        try {
            subjectService.updateSubjectById(subjectRequest.title(), subjectRequest.description(), subject_id);
            return ResponseEntity.ok("Subject updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update subject: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteSubject")
    @ResponseBody
    public ResponseEntity<String> deleteSubject(@RequestParam int subject_id) {
        try {
            subjectService.deleteSubjectById(subject_id);
            return ResponseEntity.ok("Subject deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete subject: " + e.getMessage());
        }
    }

    @PostMapping("/insertStudentAndSubject")
    @ResponseBody
    public ResponseEntity<String> addStudentAndSubject(@RequestParam("subject_id") int subject_id, @Valid StudentRequest studentRequest) {
        try {
            studentService.insertStudentAndSubject(studentRequest.first_name(), studentRequest.last_name(), studentRequest.date_of_birth(), studentRequest.group_name(), subject_id);
            return ResponseEntity.ok("Student added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add Student : " + e.getMessage());
        }
    }

    @PostMapping("/insertStudentSubject")
    @ResponseBody
    public ResponseEntity<String> addStudentSubject(@RequestParam("student_id") int student_id, @RequestParam("subject_id") int subject_id) {
        try {
            studentService.insertStudentSubject(student_id, subject_id);
            return ResponseEntity.ok("Student added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add Student : " + e.getMessage());
        }
    }

    @PutMapping("/updateStudent/{student_id}")
    @ResponseBody
    public ResponseEntity<String> updateStudent(@PathVariable("student_id") int student_id, @RequestBody @Valid StudentRequest studentRequest) {
        try {
            studentService.updateStudentById(studentRequest.first_name(), studentRequest.last_name(), studentRequest.date_of_birth(), studentRequest.group_name(), student_id);
            return ResponseEntity.ok("Student updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update student: " + e.getMessage());
        }
    }
    

    @DeleteMapping("/deleteStudent")
    @ResponseBody
    public ResponseEntity<String> deleteStudent(@RequestBody Map<String, Integer> payload) {
        try{
            int studentId = payload.get("student_id");
            int subjectId = payload.get("subject_id");
            studentService.deleteStudentById(studentId, subjectId);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Student : " + e.getMessage());
        }
    }

    @PostMapping("/insertTask")
    @ResponseBody
    public ResponseEntity<String> addTask(@Valid TaskRequestInsert taskRequest) {
        try {
            taskService.insertTask(taskRequest.title(), taskRequest.content(), taskRequest.max_score(), taskRequest.subject_id());
            return ResponseEntity.ok("Task added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add task: " + e.getMessage());
        }
    }

    @PutMapping("/updateTask/{task_id}")
    @ResponseBody
    public ResponseEntity<String> updateTask(@PathVariable("task_id") int task_id, @RequestBody @Valid TaskRequestUpdate taskRequest) {
        try {
            taskService.updateTaskById(taskRequest.title(), taskRequest.content(), taskRequest.max_score(), task_id);
            return ResponseEntity.ok("Task updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update task: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteTask")
    @ResponseBody
    public ResponseEntity<String> deleteTask(@RequestParam int task_id) {
        try {
            taskService.deleteTaskById(task_id);
            return ResponseEntity.ok("Task deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Task: " + e.getMessage());
        }
    }

    @PostMapping("/insertTaskResult")
    @ResponseBody
    public ResponseEntity<String> addTaskResult(@Valid TaskResultRequest taskResultRequest) {
        try {
            taskResultService.insertTaskResult(taskResultRequest.task_id(), taskResultRequest.student_id(), taskResultRequest.score());
            return ResponseEntity.ok("Result added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add task: " + e.getMessage());
        }
    }

    @PutMapping("/updateTaskResult/{result_id}/{score}")
    @ResponseBody
    public ResponseEntity<String> updateTaskResult(@PathVariable("result_id") int result_id, @PathVariable("score") int score) {
        try {
            taskResultService.updateTaskResultById(score, result_id);
            return ResponseEntity.ok("Result updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update task: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteTaskResult")
    @ResponseBody
    public ResponseEntity<String> deleteTaskResult(@RequestParam int result_id) {
        try {
            taskResultService.deleteTaskResultById(result_id);
            return ResponseEntity.ok("Result deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Task: " + e.getMessage());
        }
    }

}