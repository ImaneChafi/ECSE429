
@tag
Feature: remove task from to do list
  As a student, I remove an unnecessary task from my course to do list, so I can forget about it.

Background: 
    Given the server is running
    
  @tag1
   Scenario: Student removes an unnecessary task form to do list - project/task (Normal flow)
    Given the project-task exists in the backend
    When the student requests to delete project-task
    Then the project-task should be deleted from the backend

  @tag2
  Scenario: Student removes an unnecessary task form to do list - to-do/tasks (Alternate flow)
    Given the todo-taskof exists in the backend
    When the student requests to delete todo-taskof
    Then the todo-taskof should be deleted from the backend

  @tag3
  Scenario: There is no task in to todo list (Error flow)
    Given no task exist in the backend
    When the student requests to delete non-existing task
    Then no task in backend
    
   
