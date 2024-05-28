document.addEventListener("DOMContentLoaded", function() {
    var table = document.getElementById("table");
    var cells = table.getElementsByTagName("td");

    for (var i = 0; i < cells.length; i++) {
        if (cells[i].innerHTML.trim() === "" && !cells[i].classList.contains("noInputTD")) {
            var input = document.createElement("input");
            input.type = "number";
            input.setAttribute("th:data-result-id", "0");
            var taskId = cells[i].getAttribute("data-task-id");
            var studentId = cells[i].getAttribute("data-student-id");
            input.setAttribute("th:data-task-id", taskId);
            input.setAttribute("th:data-student-id", studentId);
            input.setAttribute("onchange", "insertTaskResult(this)");
            cells[i].appendChild(input);
        }
    }
});

function openModal(event, modalId) {
    form = document.getElementById(modalId).querySelector('form');
    const subjectModal = document.getElementById(modalId);

    if (modalId === 'updateSubjectModal'){
        const button = event.target;
        const subjectId = button.getAttribute('data-subject-id');
        const subjectTitle = button.getAttribute('data-subject-title');
        const subjectDescription = button.getAttribute('data-subject-description');
    
        document.getElementById('updateSubjectTitle').value = subjectTitle;
        document.getElementById('updateSubjectDescription').value = subjectDescription;
        document.getElementById('updateSubjectId').value = subjectId;
        document.getElementById('deleteSubjectButton').dataset.subjectId = subjectId;
    }

    if (modalId === 'updateStudentModal'){
        const button = event.target;
        var studentId = button.getAttribute('data-student-id');
        var firstName = button.getAttribute('data-student-first-name');
        var lastName = button.getAttribute('data-student-last-name');
        var dateOfBirth = button.getAttribute('data-student-date-of-birth');
        var groupName = button.getAttribute('data-student-group-name');

        document.getElementById('updateStudentId').value = studentId;
        document.getElementById('deleteStudentButton').dataset.studentId = studentId;
        document.getElementById('updateStudentFirstName').value = firstName;
        document.getElementById('updateStudentLastName').value = lastName;
        document.getElementById('updateStudentDateOfBirth').value = dateOfBirth;
        document.getElementById('updateStudentGroupName').value = groupName;
    }

    if (modalId === 'updateTaskModal'){
        const button = event.target;
        var taskId = button.getAttribute('data-task-id');
        var taskTitle = button.getAttribute('data-task-title');
        var taskContent = button.getAttribute('data-task-content');
        var taskMaxScore = button.getAttribute('data-task-max-score');
    
        document.getElementById('updateTaskId').value = taskId;
        document.getElementById('deleteTaskButton').dataset.taskId = taskId;
        document.getElementById('updateTaskTitle').value = taskTitle;
        document.getElementById('updateTaskContent').value = taskContent;
        document.getElementById('updateTaskMaxScore').value = taskMaxScore;
    }

    subjectModal.style.display = 'block';
    document.querySelector('.overlay').style.display = 'block';
}

function closeModals() {
    document.querySelectorAll('.modal').forEach(function (modal) {
        modal.classList.add('fade-out');
        setTimeout(() => {
            modal.style.display = 'none';
            modal.classList.remove('fade-out');
        }, 300);
    });
    document.querySelectorAll('.noStartDisplayForm').forEach(form => {
        form.style.display = 'none';
    });
    document.querySelector('.overlay').style.display = 'none';
}

function showForm(formId) {
    document.querySelectorAll('.noStartDisplayForm').forEach(form => {
        form.style.display = 'none';
    });
    document.getElementById(formId).style.display = 'grid';
}

document.addEventListener('DOMContentLoaded', function() {
    var currentUrl = window.location.href;

    if (currentUrl.includes('/subjects/')) {
        document.getElementsByClassName('table_container')[0].style.display = 'block';
    }
});

$(document).ready(function() {
    $("#insertSubjectForm, #insertNewStudentForm, #insertAlreadyExistStudentForm, #insertTaskForm").submit(function(event) {
        event.preventDefault();
        var form = $(this);
        var url = form.attr("action");
        var formData = form.serialize();

        $.post(url, formData)
            .done(function(response) {
                alert(response);
                location.reload();
            })
            .fail(function(error) {
                handleAjaxError(error);
            });
    });
});

document.getElementById('updateSubjectForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const subjectId = document.getElementById('updateSubjectId').value;
    const title = document.getElementById('updateSubjectTitle').value;
    const description = document.getElementById('updateSubjectDescription').value;

    fetch(`/updateSubject/${subjectId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title: title, description: description })
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        location.reload();
    })
    .catch(error => {
        handleAjaxError(error);
    });
});

document.getElementById('updateStudentForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const studentId = document.getElementById('updateStudentId').value;
    const firstName = document.getElementById('updateStudentFirstName').value;
    const lastName = document.getElementById('updateStudentLastName').value;
    const dateOfBirth = document.getElementById('updateStudentDateOfBirth').value;
    const groupName = document.getElementById('updateStudentGroupName').value;

    fetch(`/updateStudent/${studentId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ first_name: firstName, last_name: lastName, date_of_birth: dateOfBirth, group_name: groupName })
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        location.reload();
    })
    .catch(error => {
        handleAjaxError(error);
    });
});

document.getElementById('updateTaskForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const taskId = document.getElementById('updateTaskId').value;
    const title = document.getElementById('updateTaskTitle').value;
    const content = document.getElementById('updateTaskContent').value;
    const maxScore = document.getElementById('updateTaskMaxScore').value;

    fetch(`/updateTask/${taskId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title: title, content: content, max_score: parseFloat(maxScore) })
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        location.reload();
    })
    .catch(error => {
        handleAjaxError(error);
    });
});

function deleteSubject(event) {
    const button = event.target;
    const subject_id = button.dataset.subjectId;

    $.ajax({
        type: "DELETE",
        url: "/deleteSubject",
        data: { subject_id: subject_id },
        success: function(response) {
            alert(response);
            location.href = '/';
        },
        error: function(error) {
            handleAjaxError(error);
        }
    });

}

function deleteStudent(event) {
    const button = event.target;
    const studentId = button.dataset.studentId;
    const subjectId = button.dataset.subjectId;

    $.ajax({
        type: "DELETE",
        url: "/deleteStudent",
        data: JSON.stringify({ student_id: studentId, subject_id: subjectId }),
        contentType: "application/json",
        success: function(response) {
            alert(response);
            location.reload();
        },
        error: function(error) {
            handleAjaxError(error);
        }
    });
}

function deleteTask(event) {
    const button = event.target;
    const task_id = button.dataset.taskId;

    $.ajax({
        type: "DELETE",
        url: "/deleteTask",
        data: { task_id: task_id },
        success: function(response) {
            alert(response);
            location.reload();
        },
        error: function(error) {
            handleAjaxError(error);
        }
    });
}

function insertTaskResult(input) {
    var taskId = input.getAttribute("th:data-task-id");
    var studentId = input.getAttribute("th:data-student-id");
    var score = input.value;

    if (score < 0) {
        alert("Результат не може бути від'ємним");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/insertTaskResult",
        data: {
            task_id: taskId,
            student_id: studentId,
            score: score
        },
        success: function(response) {
            console.log(response);
            location.reload();
        },
        error: function(error) {
            handleAjaxError(error);
        }
    });
}

function updateTaskResult(input) {
    var resultId = input.dataset.resultId;
    var score  = input.value;


    if (resultId && (score  == null || score  == undefined || score  == "")) {
        $.ajax({
            type: "DELETE",
            url: "/deleteTaskResult",
            data: {
                result_id: resultId
            },
            success: function(response) {
                console.log(response);
                location.reload();
            },
            error: function(error) {
                let errorMessage = "Помилка при відправці даних до сервера";
                if (error.responseJSON && error.responseJSON.message) {
                    errorMessage += " Json: " + error.responseJSON.message;
                } else if (error.responseText) {
                    errorMessage += " Text: " + error.responseText;
                }
    
                alert(errorMessage);
            }
        })
        return;
    }

    if (score < 0) {
        console.log("Помилка: Результат не може бути менше 0");
        return;
    } 
    
    $.ajax({
        type: "PUT",
        url: "/updateTaskResult/" + resultId + "/" + score,
        success: function(response) {
            console.log(response);
            location.reload();
        },
        error: function(error) {
            handleAjaxError(error);
        }
    });
}

function handleAjaxError(error) {
    let errorMessage = "Помилка при відправці даних до сервера";
    let detailedMessage = errorMessage;
    let shortMessage = errorMessage;

    if (error.responseJSON && error.responseJSON.message) {
        detailedMessage += " JSON: " + error.responseJSON.message;
    } else if (error.responseText) {
        detailedMessage += " Text: " + error.responseText;
    }

    let match = detailedMessage.match(/ОШИБКА: (.+?)\n/);
    if (match && match[1]) {
        shortMessage += ":\n" + match[1];
    }

    console.log(detailedMessage);
    alert(shortMessage);
}