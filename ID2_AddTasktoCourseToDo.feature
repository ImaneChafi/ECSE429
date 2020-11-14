Feature: Add Task to Course ToDo list

As a student
I want to add a task to a course to do list
So that I can remember it.

  Scenario: User adds task to ToDo list (Normal Flow)
    Given user Lila has the to do list manager rest api running
     When the student requests to create a task under Todos 
      | id      | title   | done_status | description | 
      | 1 | scan paperwork | false       | scan with printer     | 
      | 2  | file paperwork | false       | file in computer | 
     Then the task with id <id>, title <title>, done_status <done_status> and description <description> should be created.
      | id      | title   | done_status | description | 
      | 1 | scan paperwork | false       | scan with printer     | 
      | 2  | file paperwork | false       | file in computer | 
      | 3  | read paperwork | false       | read updates on paperwork | 
  
  Scenario: User adds done task to ToDo list (Alternate Flow)
    Given user Lila has the to do list manager rest api running     
    When the student requests to create a done task under Todos 
      | id      | title   | done_status | description | 
      | 1 | scan paperwork | false       | scan with printer     | 
      | 2  | file paperwork | false       | file in computer | 
     Then the task with id <id>, title <title>, done_status <true> and description <description> should be created.
      | id      | title   | done_status | description | 
      | 1 | scan paperwork | false       | scan with printer     | 
      | 2  | file paperwork | false       | file in computer | 
      | 3  | rest | true       | take nap | 
  
  Scenario: User attempts to create a task with an id that already exists (Error Flow)
    Given user Lila has the to do list manager rest api running
     When requesting to create a task with id <1>, title <title>, done_status <done_status> and description <description>
     Then a "Error 404" error message is issued
     And the new task is not created. 