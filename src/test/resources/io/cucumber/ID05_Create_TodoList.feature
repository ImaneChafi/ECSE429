Feature: create a to do list for a new class
  As a student, I create a to do list for a new class I am taking, so I can manage course work.

  Scenario: Student creates to do list for a course with title (Normal flow)
    Given the todoManager is running at the backend
    When the student requests to create a to do list with title
    Then the to do list with title should be created


  Scenario: Student creates to do list for a course with title and description (Alternate flow)
    Given the todoManager is running at the backend
    When the student requests to create a to do list with title and description
    Then the to do with title and description should be created


  Scenario: The todoManager is not running at the backend (Error flow)
    Given The todoManager is not running at the backend
    When the student requests to create a to do list
    Then start backend first


  Scenario: The id is specified when creating a to do list (Error flow)
    Given the todoManager is running at the backend
    When the student requests to create a to do with id defined
    Then an error message xx should be displayed