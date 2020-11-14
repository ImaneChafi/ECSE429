Feature: Query Incomplete tasks

As a student
I want to query all incomplete tasks for a class I am taking
So that I can help manage my time

  Background: 
    Given the following tasks are in the system todos list
     | id      | title   | done_status | description | 
     | 1 | scan paperwork | false       | scan with printer     | 
     | 2  | file paperwork | true       | file in computer | 
   
  Scenario: Student requests list of all incomplete tasks (Normal Flow)
  
    Given student "Boba Fett" has the to do list manager rest api running
     When the student requests a list of incomplete tasks
     Then the following list of tasks is generated
     | id      | title   | done_status | description | 
     | 1 | scan paperwork | false       | scan with printer     | 
  
  Scenario: Student requests list of all tasks (Alternate Flow)
  
     Given student "Boba Fett" has the to do list manager rest api running
     When the student requests a list of all tasks
     Then the following list of tasks is generated
     | id      | title   | done_status | description | 
     | 1 | scan paperwork | false       | scan with printer     | 
     | 2  | file paperwork | true       | file in computer |   

  Scenario: Student requests list of no incomplete tasks (Error Flow)
  
    Given student "Boba Fett" is logged in
     And the following tasks are in the system todos list
     | id      | title   | done_status | description | 
     | 1 | scan paperwork | true       | scan with printer     | 
     | 2  | file paperwork | true       | file in computer | 
     When the student requests a list of incomplete tasks
     Then no tasks come up 