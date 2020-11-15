$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri('cucumberJava\ID2_AddTasktoCourseToDo.feature');
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Author: your.email@your.domain.com"
    },
    {
      "line": 2,
      "value": "#Keywords Summary :"
    },
    {
      "line": 3,
      "value": "#Feature: List of scenarios."
    },
    {
      "line": 4,
      "value": "#Scenario: Business rule through list of steps with arguments."
    },
    {
      "line": 5,
      "value": "#Given: Some precondition step"
    },
    {
      "line": 6,
      "value": "#When: Some key actions"
    },
    {
      "line": 7,
      "value": "#Then: To observe outcomes or validation"
    },
    {
      "line": 8,
      "value": "#And,But: To enumerate more Given,When,Then steps"
    },
    {
      "line": 9,
      "value": "#Scenario Outline: List of steps for data-driven as an Examples and \u003cplaceholder\u003e"
    },
    {
      "line": 10,
      "value": "#Examples: Container for s table"
    },
    {
      "line": 11,
      "value": "#Background: List of steps run before each of the scenarios"
    },
    {
      "line": 12,
      "value": "#\"\"\" (Doc Strings)"
    },
    {
      "line": 13,
      "value": "#| (Data Tables)"
    },
    {
      "line": 14,
      "value": "#@ (Tags/Labels):To group Scenarios"
    },
    {
      "line": 15,
      "value": "#\u003c\u003e (placeholder)"
    },
    {
      "line": 16,
      "value": "#\"\""
    },
    {
      "line": 17,
      "value": "## (Comments)"
    },
    {
      "line": 18,
      "value": "#Sample Feature Definition Template"
    }
  ],
  "line": 20,
  "name": "Add Task to Course ToDo list",
  "description": "As a student\nI want to add a task to a course to do list\nSo that I can remember it.",
  "id": "add-task-to-course-todo-list",
  "keyword": "Feature",
  "tags": [
    {
      "line": 19,
      "name": "@tag"
    }
  ]
});
formatter.scenario({
  "line": 26,
  "name": "User adds task to ToDo list (Normal Flow)",
  "description": "",
  "id": "add-task-to-course-todo-list;user-adds-task-to-todo-list-(normal-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 25,
      "name": "@tag1"
    }
  ]
});
formatter.step({
  "line": 27,
  "name": "user Lila has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "the student requests to create a task under Todos",
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "the task with id \u003cid\u003e, title \u003ctitle\u003e, done_status \u003cdone_status\u003e and description \u003cdescription\u003e should be created",
  "keyword": "Then "
});
formatter.match({
  "location": "AddTasktoCourse.main_page_normal()"
});
formatter.result({
  "duration": 761586900,
  "status": "passed"
});
formatter.match({
  "location": "AddTasktoCourse.task_todos_post()"
});
formatter.result({
  "duration": 340994700,
  "status": "passed"
});
formatter.match({
  "location": "AddTasktoCourse.task_todos_id_get()"
});
formatter.result({
  "duration": 12623600,
  "status": "passed"
});
formatter.scenario({
  "line": 32,
  "name": "User adds done task to ToDo list (Alternate Flow)",
  "description": "",
  "id": "add-task-to-course-todo-list;user-adds-done-task-to-todo-list-(alternate-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 31,
      "name": "@tag2"
    }
  ]
});
formatter.step({
  "line": 33,
  "name": "user Lila has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.step({
  "line": 34,
  "name": "the student requests to create a done task under Todos",
  "keyword": "When "
});
formatter.step({
  "line": 35,
  "name": "the task with id \u003cid\u003e, title \u003ctitle\u003e, done_status \u003ctrue\u003e and description \u003cdescription\u003e should be created.",
  "keyword": "Then "
});
formatter.match({
  "location": "AddTasktoCourse.main_page_normal()"
});
formatter.result({
  "duration": 6322800,
  "status": "passed"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({
  "location": "AddTasktoCourse.test_todos_id_get()"
});
formatter.result({
  "status": "skipped"
});
formatter.scenario({
  "line": 38,
  "name": "User attempts to create a task with an id that already exists (Error Flow)",
  "description": "",
  "id": "add-task-to-course-todo-list;user-attempts-to-create-a-task-with-an-id-that-already-exists-(error-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 37,
      "name": "@tag3"
    }
  ]
});
formatter.step({
  "line": 39,
  "name": "user Lila has the to do list manager rest api running.",
  "keyword": "Given "
});
formatter.step({
  "line": 40,
  "name": "requesting to create a task with id \u003c1\u003e, title \u003ctitle\u003e, done_status \u003cdone_status\u003e and description \u003cdescription\u003e",
  "keyword": "When "
});
formatter.step({
  "line": 41,
  "name": "a \"Error 404\" error message is issued",
  "keyword": "Then "
});
formatter.step({
  "line": 42,
  "name": "the new task is not created",
  "keyword": "And "
});
formatter.match({
  "location": "AddTasktoCourse.main_page_error()"
});
formatter.result({
  "duration": 12400900,
  "status": "passed"
});
formatter.match({
  "location": "AddTasktoCourse.task_todos_post_error()"
});
formatter.result({
  "duration": 4230000,
  "status": "passed"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});