
@tag
Feature: Categorize task
As a student
I categorize tasks as High, Medium or Low priority on my course to do list
So I can better manage my time.

Background: 
	Given user Ninja has the to do list manager rest api running
	 
  @tag1
  Scenario: User categorizes tasks (Normal Flow)
    When requesting to categorize Priority of TaskA from High to Low
    Then the Priority of TaskA is categorized as Low

  @tag2
  Scenario: User categorizes tasks (ALternate Flow)
    When requesting to categorize Priority of TaskA from High to High
    Then the Priority of TaskA remains as High

  @tag3
 	Scenario: User attempts to adjust task completion (Error Flow)
     When requesting to categorize Priority of TaskA from High to invalid priority
     Then an "404" error message is issued  