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
Feature: Add Task to Course ToDo list
  As a student
	I want to add a task to a course to do list
	So that I can remember it.

  @tag1
  Scenario: User adds task to ToDo list (Normal Flow)
    Given user Lila has the to do list manager rest api running
    When the student requests to create a task under Todos 
    Then the task with id <id>, title <title>, done_status <done_status> and description <description> should be created

  @tag2
  Scenario: User adds done task to ToDo list (Alternate Flow)
    Given user Lila has the to do list manager rest api running    
    When the student requests to create a done task under Todos 
    Then the task with id <id>, title <title>, done_status <true> and description <description> should be created.

  @tag3
  Scenario: User attempts to create a task with an id that already exists (Error Flow)
    Given user Lila has the to do list manager rest api running.
    When requesting to create a task with id <1>, title <title>, done_status <done_status> and description <description>
    Then a "Error 404" error message is issued
 		And the new task is not created

