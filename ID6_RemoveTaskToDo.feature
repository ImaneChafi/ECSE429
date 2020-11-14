Feature: Remove Todo list 

As a student
I want to remove a todo list for a class which I am no longer taking
So that I can declutter my schedule.

  Scenario: User removes ToDo list (Normal Flow)
    Given user Lila has the to do list manager rest api running
     When the student requests to delete a todo list using id <1>
      | id      | title   | done_status | description | 
      | 1 | scan paperwork | false       | scan with printer     | 
      | 2  | file paperwork | false       | file in computer | 
     Then the todo with id <1>, title <scan paperwork>, done_status <false> and description <scan with printer> should be deleted.
      | id      | title   | done_status | description | 
      | 2  | file paperwork | false       | file in computer | 
  
  Scenario: User removes ToDo list without description (Alternate Flow)
    Given user Lila has the to do list manager rest api running     
     When the student requests to delete a todo list with description <> using id <2>
      | id      | title   | done_status | description | 
      | 1 | scan paperwork | false       | scan with printer     | 
      | 2  | file paperwork | false       | | 
     Then the todo with id <2>, title <file paperwork>, done_status <false> and description <> should be deleted.
      | id      | title   | done_status | description | 
      | 1 | scan paperwork | false       | scan with printer     | 

  Scenario: User attempts to delete a todo with an unexistant id (Error Flow)
    Given user Lila has the to do list manager rest api running
     When requesting to delete a task with id <20>, title <title>, done_status <done_status> and description <description>
     | id      | title   | done_status | description | 
     | 1 | scan paperwork | false       | scan with printer     | 
     Then a "Error 404" error message is issued
     And the todo with id <20> is not deleted. 