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

import java.util.Objects;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ProgController.class);

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
        this.subjectService = Objects.requireNonNull(subjectService, "subjectService is null");
        this.studentService = Objects.requireNonNull(studentService, "studentService is null");
        this.taskService = Objects.requireNonNull(taskService, "taskService is null");
        this.taskResultService = Objects.requireNonNull(taskResultService, "taskResultService is null");
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
            logger.info("POST /insertSubject - Спроба додати предмет: {}", subjectRequest.title());
            logger.debug("Деталі предмета: title='{}', description='{}'", subjectRequest.title(), subjectRequest.description());

            subjectService.insertSubject(subjectRequest.title(), subjectRequest.description());
            logger.info("Предмет успішно додано: {}", subjectRequest.title());

            return ResponseEntity.ok("Subject added successfully");
        } catch (Exception e) {
            logger.error("Помилка при додаванні предмету: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to add subject: " + e.getMessage());
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
            logger.info("PUT /updateSubject/{} - Спроба оновити предмет", subject_id);
            logger.debug("Деталі нового предмета: title='{}', description='{}'", subjectRequest.title(), subjectRequest.description());
            logger.info("Updating subject with ID: {}", subject_id);

            subjectService.updateSubjectById(subjectRequest.title(), subjectRequest.description(), subject_id);
            return ResponseEntity.ok("Subject updated successfully");
        } catch (Exception e) {
            logger.error("Failed to update subject with ID {}: {}", subject_id, e.getMessage());
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
            logger.info("DELETE /deleteSubject - Спроба видалити предмет з ID: {}", subject_id);
            logger.info("Deleting subject with ID: {}", subject_id);

            subjectService.deleteSubjectById(subject_id);
            return ResponseEntity.ok("Subject deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete subject with ID {}: {}", subject_id, e.getMessage());
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
            logger.info("POST /insertStudentAndSubject - Додавання студента до предмета з ID: {}", subject_id);
            logger.debug("Дані студента: {} {}", studentRequest.first_name(), studentRequest.last_name());
            logger.info("Adding student to subject ID: {}", subject_id);

            studentService.insertStudentAndSubject(studentRequest.first_name(), studentRequest.last_name(), studentRequest.date_of_birth(), studentRequest.group_name(), subject_id);
            return ResponseEntity.ok("Student added successfully");
        } catch (Exception e) {
            logger.error("Failed to add student to subject ID {}: {}", subject_id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add student: " + e.getMessage());
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
            logger.info("POST /insertStudentSubject - Додавання студента з ID {} до предмета з ID {}", student_id, subject_id);
            logger.info("Adding existing student ID: {} to subject ID: {}", student_id, subject_id);

            studentService.insertStudentSubject(student_id, subject_id);
            return ResponseEntity.ok("Student added successfully");
        } catch (Exception e) {
            logger.error("Failed to add student ID {} to subject ID {}: {}", student_id, subject_id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add student: " + e.getMessage());
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
            logger.info("PUT /updateStudent/{} - Спроба оновити студента", student_id);
            logger.debug("Дані: {} {}, {}, {}", studentRequest.first_name(), studentRequest.last_name(), studentRequest.date_of_birth(), studentRequest.group_name());
            logger.info("Updating student with ID: {}", student_id);

            studentService.updateStudentById(studentRequest.first_name(), studentRequest.last_name(), studentRequest.date_of_birth(), studentRequest.group_name(), student_id);
            return ResponseEntity.ok("Student updated successfully");
        } catch (Exception e) {
            logger.error("Failed to update student with ID {}: {}", student_id, e.getMessage());
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
        try {
            int studentId = payload.get("student_id");
            int subjectId = payload.get("subject_id");
            logger.info("DELETE /deleteStudent - Спроба видалити студента з ID: {} з предмету ID: {}", studentId, subjectId);
            logger.info("Deleting student with ID: {} from subject ID: {}", studentId, subjectId);
            
            studentService.deleteStudentById(studentId, subjectId);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete student: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete student: " + e.getMessage());
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
            logger.info("POST /insertTask - Спроба додати завдання: {}", taskRequest.title());
            logger.debug("Зміст: '{}', Максимальний бал: {}, ID предмету: {}", taskRequest.content(), taskRequest.max_score(), taskRequest.subject_id());
            logger.info("Inserting task for subject ID: {}", taskRequest.subject_id());

            taskService.insertTask(taskRequest.title(), taskRequest.content(), taskRequest.max_score(), taskRequest.subject_id());
            return ResponseEntity.ok("Task added successfully");
        } catch (Exception e) {
            logger.error("Failed to add task: {}", e.getMessage());
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
            logger.info("PUT /updateTask/{} - Оновлення завдання: {}", task_id, taskRequest.title());
            logger.info("Updating task with ID: {}", task_id);

            taskService.updateTaskById(taskRequest.title(), taskRequest.content(), taskRequest.max_score(), task_id);
            return ResponseEntity.ok("Task updated successfully");
        } catch (Exception e) {
            logger.error("Failed to update task with ID {}: {}", task_id, e.getMessage());
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
            logger.info("DELETE /deleteTask - Спроба видалити завдання з ID: {}", task_id);
            logger.info("Deleting task with ID: {}", task_id);

            taskService.deleteTaskById(task_id);
            return ResponseEntity.ok("Task deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete task with ID {}: {}", task_id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task: " + e.getMessage());
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
            logger.info("POST /insertTaskResult - Спроба додати результат для завдання ID: {} і студента ID: {}", taskResultRequest.task_id(), taskResultRequest.student_id());
            logger.debug("Оцінка: {}", taskResultRequest.score());
            logger.info("Inserting result for task ID: {} and student ID: {}", taskResultRequest.task_id(), taskResultRequest.student_id());

            taskResultService.insertTaskResult(taskResultRequest.task_id(), taskResultRequest.student_id(), taskResultRequest.score());
            return ResponseEntity.ok("Result added successfully");
        } catch (Exception e) {
            logger.error("Failed to insert result: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add result: " + e.getMessage());
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
            logger.info("PUT /updateTaskResult/{}/{} - Оновлення результату: нова оцінка {}", result_id, score, score);
            logger.info("Updating result ID: {} with new score: {}", result_id, score);

            taskResultService.updateTaskResultById(score, result_id);
            return ResponseEntity.ok("Result updated successfully");
        } catch (Exception e) {
            logger.error("Failed to update result ID {}: {}", result_id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update result: " + e.getMessage());
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
            logger.info("DELETE /deleteTaskResult - Спроба видалити результат з ID: {}", result_id);
            logger.info("Deleting task result with ID: {}", result_id);

            taskResultService.deleteTaskResultById(result_id);
            return ResponseEntity.ok("Result deleted successfully");
        } catch (Exception e) {
            logger.error("Failed to delete task result with ID {}: {}", result_id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete result: " + e.getMessage());
        }
    }

}