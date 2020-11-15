Feature: Categorize task
As a student
I categorize tasks as High, Medium or Low priority on my course to do list
So I can better manage my time.

 
  Scenario: User categorizes tasks (Normal Flow)
    Given user Fizbin has the to do list manager rest api running
    When requesting to categorize Priority of TaskA from High to Low
    Then the Priority of TaskA is categorized as Low


  Scenario: User categorizes tasks (ALternate Flow)
    Given user Fizbin has the to do list manager rest api running
    When requesting to categorize Priority of TaskA from High to High
    Then the Priority of TaskA remains as High

  Scenario: User attempts to adjust task completion (Error Flow)
    Given user Fizbin has the to do list manager rest api running
     When requesting to categorize Priority of TaskA from High to invalid priority
     Then a "Error 404" error message is issued