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
Feature: Query Incomplete tasks
As a student
I want to query all incomplete tasks for a class I am taking
So that I can help manage my time

 @tag1
  Scenario: Student requests list of all incomplete tasks (Normal Flow)
    Given student "Boba Fett" has the to do list manager rest api running
    When the student requests a list of incomplete tasks
    Then the list of incomplete tasks is generated

  @tag2
  Scenario: Student requests list of completed tasks (Alternate Flow)
    Given student "Freddy Mercury" has the to do list manager rest api running    
    When the student requests a list of tasks with doneStatus <true> 
		Then the list of done tasks is generated
		
  @tag3
  Scenario: User attempts to query for unexistant task status (Error Flow)
    Given user Diana has the to do list manager rest api running  
    When the student requests a list of tasks with doneStatus <maybe>
    Then no task show up
