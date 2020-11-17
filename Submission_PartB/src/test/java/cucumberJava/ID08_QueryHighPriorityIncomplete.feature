@tag
Feature: Query Incomplete High priority tasks
	As a student
	I want to query all incomplete High priority tasks from all my classes, 
	to identify my short-term goals

Background: 
  Given student Marcus has the to do list manager rest api running
	
  @tag1
 Scenario: Student requests list of all incomplete High priority tasks (Normal Flow)
    When the student requests a list of incomplete high priority tasks
    Then the list of incomplete tasks has been generated


  @tag2
  Scenario: Student requests list of completed High priority tasks (Alternate Flow)
    When the student requests a list of tasks with doneStatus <true> for High priority tasks
		Then the list of tasks with completed high priorities is created

  @tag3
  Scenario: User attempts to query for unexistant task status for High priority tasks (Error Flow)
    When Marcus requests a list of tasks with doneStatus <maybe>
    Then there is no task that show up
