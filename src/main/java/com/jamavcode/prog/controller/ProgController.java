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

/**
 * Контролер, який відповідає за обробку запитів, пов'язаних з управлінням навчальним процесом.
 */
@Controller
public class ProgController {

    private final SubjectService subjectService;
    private final StudentService studentService;
    private final TaskService taskService;
    private final TaskResultService taskResultService;

    /**
     * Конструктор класу контролера.
     *
     * @param subjectService    Сервіс для взаємодії з предметами.
     * @param studentService    Сервіс для взаємодії зі студентами.
     * @param taskService       Сервіс для взаємодії з завданнями.
     * @param taskResultService Сервіс для взаємодії з результатами завдань.
     */
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

    /**
     * Метод для завантаження загальних атрибутів на сторінку.
     *
     * @param model Об'єкт моделі, до якого будуть додані атрибути.
     */
    private void loadCommonAttributes(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("pageTitle", "Керування навчанням");
        model.addAttribute("teacherName", "Викладач: Колесніков Валерій");
        model.addAttribute("subjects", subjects);
    }

    /**
     * Метод для обробки запитів на головну сторінку.
     *
     * @param model Об'єкт моделі для передачі даних на сторінку.
     * @return Рядок, що представляє шлях до HTML-шаблону сторінки.
     */
    @GetMapping("/")
    public String index(Model model) {
        loadCommonAttributes(model);
        return "index";
    }

    /**
     * Метод для обробки запитів на сторінку з певним предметом.
     *
     * @param model     Об'єкт моделі для передачі даних на сторінку.
     * @param subject_id Ідентифікатор предмету.
     * @return Рядок, що представляє шлях до HTML-шаблону сторінки.
     */
    @GetMapping(value = "/subjects/{subject_id:\\d+}")
    public String indexWithSubject(Model model, @PathVariable int subject_id) {
        loadCommonAttributes(model);
        List<Student> students = studentService.getStudentBySubjectId(subject_id);
        List<Student> studentsReverse = studentService.getStudentBySubjectIdReverse(subject_id);
        List<Task> tasks = taskService.getTasksBySubjectId(subject_id);
        List<TaskResult> taskResults = taskResultService.getTaskResultBySubjectId(subject_id);
        List<TaskResult> taskResultsSum = taskResultService.getTaskResultSumForStudentSubject();
        List<TaskResult> taskResultsAverTask = taskResultService.getTaskResultAverageForTaskSubject();
        List<TaskResult> taskResultsAverSub = taskResultService.getTaskResultAverageForSubject();
        model.addAttribute("needSubject", subjectService.getSubjectById(subject_id));
        model.addAttribute("students", students);
        model.addAttribute("studentsReverse", studentsReverse);
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskResults", taskResults);
        model.addAttribute("taskResultsSum", taskResultsSum);
        model.addAttribute("taskResultsAverTask", taskResultsAverTask);
        model.addAttribute("taskResultsAverSub", taskResultsAverSub);
        return "index";
    }

    /**
     * Метод для обробки POST-запитів на додавання нового предмету.
     *
     * @param subjectRequest Об'єкт, що містить дані про новий предмет.
     * @return Об'єкт ResponseEntity з повідомленням про успішне додавання предмету або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки PUT-запитів на оновлення інформації про предмет за його ідентифікатором.
     *
     * @param subject_id    Ідентифікатор предмету, який потрібно оновити.
     * @param subjectRequest Об'єкт, що містить оновлені дані про предмет.
     * @return Об'єкт ResponseEntity з повідомленням про успішне оновлення предмету або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки DELETE-запитів на видалення предмету за його ідентифікатором.
     *
     * @param subject_id Ідентифікатор предмету, який потрібно видалити.
     * @return Об'єкт ResponseEntity з повідомленням про успішне видалення предмету або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки POST-запитів на додавання студента та його зв'язку з предметом.
     *
     * @param subject_id      Ідентифікатор предмету, до якого додається студент.
     * @param studentRequest  Об'єкт, що містить дані про нового студента.
     * @return Об'єкт ResponseEntity з повідомленням про успішне додавання студента або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки POST-запитів на додавання студента до предмету.
     *
     * @param student_id  Ідентифікатор студента, який додається до предмету.
     * @param subject_id  Ідентифікатор предмету, до якого додається студент.
     * @return Об'єкт ResponseEntity з повідомленням про успішне додавання студента або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки PUT-запитів на оновлення інформації про студента за його ідентифікатором.
     *
     * @param student_id      Ідентифікатор студента, який потрібно оновити.
     * @param studentRequest  Об'єкт, що містить оновлені дані про студента.
     * @return Об'єкт ResponseEntity з повідомленням про успішне оновлення студента або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки DELETE-запитів на видалення студента.
     *
     * @param payload Мапа, що містить ідентифікатор студента (student_id) та ідентифікатор предмету (subject_id).
     * @return Об'єкт ResponseEntity з повідомленням про успішне видалення студента або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки POST-запитів на додавання завдання.
     *
     * @param taskRequest Об'єкт, що містить дані про нове завдання.
     * @return Об'єкт ResponseEntity з повідомленням про успішне додавання завдання або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки PUT-запитів на оновлення інформації про завдання за його ідентифікатором.
     *
     * @param task_id      Ідентифікатор завдання, яке потрібно оновити.
     * @param taskRequest  Об'єкт, що містить оновлені дані про завдання.
     * @return Об'єкт ResponseEntity з повідомленням про успішне оновлення завдання або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки DELETE-запитів на видалення завдання.
     *
     * @param task_id  Ідентифікатор завдання, яке потрібно видалити.
     * @return Об'єкт ResponseEntity з повідомленням про успішне видалення завдання або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки POST-запитів на додавання результату завдання.
     *
     * @param taskResultRequest Об'єкт, що містить дані про новий результат завдання.
     * @return Об'єкт ResponseEntity з повідомленням про успішне додавання результату завдання або повідомленням про помилку у випадку виникнення виключення.
     */
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

    /**
     * Метод для обробки PUT-запитів на оновлення результату завдання за його ідентифікатором.
     *
     * @param result_id Ідентифікатор результату завдання, який потрібно оновити.
     * @param score     Оцінка, яку потрібно встановити для результату завдання.
     * @return Об'єкт ResponseEntity з повідомленням про успішне оновлення результату завдання або повідомленням про помилку у випадку виникнення виключення.
     */
    @PutMapping("/updateTaskResult/{result_id}/{score}")
    @ResponseBody
    public ResponseEntity<String> updateTaskResult(@PathVariable("result_id") int result_id, @PathVariable("score") double score) {
        try {
            taskResultService.updateTaskResultById(score, result_id);
            return ResponseEntity.ok("Result updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update task: " + e.getMessage());
        }
    }

    /**
     * Метод для обробки DELETE-запитів на видалення результату завдання.
     *
     * @param result_id Ідентифікатор результату завдання, який потрібно видалити.
     * @return Об'єкт ResponseEntity з повідомленням про успішне видалення результату завдання або повідомленням про помилку у випадку виникнення виключення.
     */
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