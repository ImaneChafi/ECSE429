Feature: remove task from to do list
  As a student, I remove an unnecessary task from my course to do list, so I can forget about it.

  Background:
    Given the following projects is in the system with a todo associated
    | project title | completed  | active  | project description | todo title | doneStatus | todo description    |
    | ECSE429       | false      | false   | software validation | partA      | true       | exploratory testing |
    And the following todos is in the system with a project associated
    | project title | completed  | active  | project description | todo title | doneStatus | todo description    |
    | ECSE428       | false      | false   | SE practice         | next term  | false      | next term course    |

  Scenario: Student removes an unnecessary task form to do list - project/task (Normal flow)
    Given the project-task exists in the backend
    When the student requests to delete project-task
    Then the project-task should be deleted from the backend


  Scenario: Student removes an unnecessary task form to do list - to-do/tasks (Alternate flow)
    Given the todo-taskof exists in the backend
    When the student requests to delete todo-taskof
    Then the todo-taskof should be deleted from the backend


  Scenario: There is no task in to todo list (Error flow)
    Given no task exist in the backend
    When the student requests to delete non-existing task
    Then no task in backend


  Scenario: The todoManager is not running at the backend (Error flow)
    Given The todoManager is not running at the backend
    When the student requests to delete task
    Then start backend first