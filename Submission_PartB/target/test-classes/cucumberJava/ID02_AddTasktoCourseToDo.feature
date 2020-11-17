
@tag
Feature: Add Task to Course ToDo list
  As a student
	I want to add a task to a course to do list
	So that I can remember it.
	
	Background: 
   Given user Lila has the to do list manager rest api running

  @tag1
  Scenario: User adds task to ToDo list (Normal Flow)
    When the student requests to create a task under Todos 
    Then the task with id <id>, title <title>, done_status <done_status> and description <description> should be created

  @tag2
  Scenario: User adds done task to ToDo list (Alternate Flow)
    When the student requests to create a done task under Todos 
    Then the task with id <id>, title <title>, done_status <true> and description <description> should be created.

  @tag3
  Scenario: User attempts to create a task with an id that already exists (Error Flow)
    When requesting to create a task with id <1>, title <title>, done_status <done_status> and description <description>
    Then a "Error 404" error message is issued
 		And the new task is not created

