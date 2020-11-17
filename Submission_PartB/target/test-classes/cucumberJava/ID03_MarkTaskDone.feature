#Author: Mahroo

@tag
Feature: Mark a task as done

As a student
I mark a task as done on my course to do list
So I can track my accomplishments.

Background: 
  Given user Mark has the to do list manager rest api running
	
  @tag1
  Scenario: User adjusts task completion (Normal Flow)
     When requesting to change doneStatus from false to true for a Task with id "3"
     Then doneStatus changes from false to true

  @tag2
   Scenario: User adjusts task completion (ALternate Flow)
     When requesting to change doneStatus from true to true for a Task with id "3"
     Then doneStatus remains the same
     
   @tag3
   Scenario: User attempts to adjust task completion (Error Flow)
     When requesting to mark an UnMarked task as Done for TaskA
     Then an error message is thrown

