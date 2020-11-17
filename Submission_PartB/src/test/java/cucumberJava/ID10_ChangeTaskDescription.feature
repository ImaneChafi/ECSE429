#Feature file for Changing the Task Description
@tag
Feature: Change Task Description
As a student
I want to change a task description
So that it better represents the work to do.

Background: 
  Given user Fizbin has the to do list manager rest api running
	
  @tag1
  Scenario: User changes task description (Normal Flow)
    When requesting a change of description from <OldDescription> to <NewDescription> for TaskA
    Then the description of TaskA is <NEwDescription>.

  @tag2
  Scenario: User changes task description (Alternate Flow)
    When requesting a change of description from <OldDescription> to <OldDescription> for TaskA
    Then the description of TaskA is <OldDescription>.

  @tag3
  Scenario: User attempts to change task description (Error Flow)
    When requesting a change of description from <OldDescription> to <NewDescription> for TaskA
    Then a "Error 404" error message is issued
