Feature: Task Service
  Testing key functionalities of TaskService.

  Scenario: Retrieve tasks by subject ID
    Given the subject ID is 1
    When I retrieve tasks for this subject
    Then I should get a list of tasks associated with subject ID 1

  Scenario: Retrieve task by task ID
    Given a task with ID 101 exists
    When I retrieve the task with ID 101
    Then the task details should match the stored data

  Scenario: Insert a new task
    Given I have the following task details:
      | title      | content      | max_score | subject_id |
      | "Task 1"   | "Content 1"  | 10.0      | 1          |
    When I insert the task
    Then the task should be retrievable with its ID
