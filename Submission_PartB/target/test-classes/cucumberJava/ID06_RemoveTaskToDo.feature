
@tag
Feature: Remove Todo list 
  As a student
I want to remove a todo list for a class which I am no longer taking
So that I can declutter my schedule.

Background: 
  Given user Lila has the to do list manager rest api running  .
	
  @tag1
  Scenario: User removes ToDo list (Normal Flow)
    When the student requests to delete a todo list task using id <id>
    Then the task with id <id>, title <title>, done_status <done_status> and description <description> should be deleted

  @tag2
  Scenario: User removes ToDo list without description (Alternate Flow)
    When the student requests to delete a todo list with description <> using id <id> 
    Then the todo with id <id>, title <title>, done_status <done_status> and description <> should be deleted.

  @tag3
  Scenario: User attempts to delete a todo with an unexistant id (Error Flow)
    When requesting to delete a task with id <20>, title <title>, done_status <done_status> and description <description>
    Then an "Error" error message is issued.
 		And the todo with id <20> is not deleted. 
