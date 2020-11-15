#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Remove Todo list 
  As a student
I want to remove a todo list for a class which I am no longer taking
So that I can declutter my schedule.

  @tag1
  Scenario: User removes ToDo list (Normal Flow)
    Given user Lila has the to do list manager rest api running  .
    When the student requests to delete a todo list task using id <id>
    Then the task with id <id>, title <title>, done_status <done_status> and description <description> should be deleted

  @tag2
  Scenario: User removes ToDo list without description (Alternate Flow)
    Given user Lila has the to do list manager rest api running .    
    When the student requests to delete a todo list with description <> using id <id> 
    Then the todo with id <id>, title <title>, done_status <done_status> and description <> should be deleted.

  @tag3
  Scenario: User attempts to delete a todo with an unexistant id (Error Flow)
    Given user Lila has the to do list manager rest api  running  
    When requesting to delete a task with id <20>, title <title>, done_status <done_status> and description <description>
    Then a "Error 404" error message is issued.
 		And the todo with id <20> is not deleted. 
