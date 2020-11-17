
@tag
Feature: create a to do list for a new class
  As a student, I create a to do list for a new class I am taking, so I can manage course work.

Background: 
  Given the todoManager is running at the backend
	
  @tag1
   Scenario: Student creates to do list for a course with title (Normal flow)
    When the student requests to create a to do list with title
    Then the to do list with title should be created

  @tag2
  Scenario: Student creates to do list for a course with title and description (Alternate flow)
    When the student requests to create a to do list with title and description
    Then the to do with title and description should be created
    
  @tag4
	 Scenario: The id is specified when creating a to do list (Error flow)
    When the student requests to create a to do with id defined
    Then cannot create with id