Feature: Adjust Task Priority

As a student
I want to adjust the priority of a task
So that it helps better manage my time.

  Scenario: User adjusts task priority (Normal Flow)
    Given user Fizbin has the to do list manager rest api running
     When requesting a change of priority from OldPriority to NewPriority for TaskA
     Then the priority of TaskA is NewPriority.
  
  Scenario: User adjusts task priority (ALternate Flow)
    Given user Fizbin has the to do list manager rest api running
     When requesting a change of priority from OldPriority to OldPriority for TaskA
     Then the priority of TaskA remains as OldPriority.
  
  Scenario: User attempts to adjust task priority (Error Flow)
    Given user Fizbin has the to do list manager rest api running
     When requesting a change of priority from OldPriority to NewPriority for TaskA
     Then a "Error 404" error message is issued
      And the priority of TaskA remains as OldPriority
