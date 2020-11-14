Feature: Change Task Description

As a student
I want to change a task description
So that it better represents the work to do.

  Scenario: User changes task description (Normal Flow)
    Given user Fizbin has the to do list manager rest api running
     When requesting a change of description from OldDescription to NewDescription for TaskA
     Then the description of TaskA is NEwDescription.
  
  Scenario: User changes task description (Alternate Flow)
    Given user Fizbin has the to do list manager rest api running
     When requesting a change of description from OldDescription to OldDescription for TaskA
     Then the description of TaskA is OldDescription.
  
  Scenario: User attempts to change task description (Error Flow)
    Given user Fizbin has the to do list manager rest api running
     When requesting a change of description from OldDescription to NewDescription for TaskA
     Then a "Error 404" error message is issued
      And the description of TaskA remains as OldDescription
