#Author: Imane Chafi
@tag
Feature: Query Incomplete tasks
As a student
I want to query all incomplete tasks for a class I am taking
So that I can help manage my time

Background: 
   Given student Boba Fett has the to do list manager rest api running
	
 @tag1
  Scenario: Student requests list of all incomplete tasks (Normal Flow)
    When the student requests a list of incomplete tasks
    Then the list of incomplete tasks is generated

  @tag2
  Scenario: Student requests list of completed tasks (Alternate Flow)
    When the student requests a list of tasks with doneStatus <true> 
		Then the list of done tasks is generated
		
  @tag3
  Scenario: User attempts to query for unexistant task status (Error Flow)
    When the student requests a list of tasks with doneStatus <maybe>
    Then no task show up
