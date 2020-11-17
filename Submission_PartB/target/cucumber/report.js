$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri('cucumberJava\ID01_CategorizeTasks.feature');
formatter.feature({
  "line": 3,
  "name": "Categorize task",
  "description": "As a student\nI categorize tasks as High, Medium or Low priority on my course to do list\nSo I can better manage my time.",
  "id": "categorize-task",
  "keyword": "Feature",
  "tags": [
    {
      "line": 2,
      "name": "@tag"
    }
  ]
});
formatter.background({
  "line": 8,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 9,
  "name": "user Ninja has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID01CategorizeTasks.user_ninja_has_the_to_do_list_manager_rest_api_running()"
});
formatter.result({
  "duration": 4795537400,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID01CategorizeTasks.user_ninja_has_the_to_do_list_manager_rest_api_running(ID01CategorizeTasks.java:32)\r\n\tat ✽.Given user Ninja has the to do list manager rest api running(cucumberJava\\ID01_CategorizeTasks.feature:9)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 12,
  "name": "User categorizes tasks (Normal Flow)",
  "description": "",
  "id": "categorize-task;user-categorizes-tasks-(normal-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 11,
      "name": "@tag1"
    }
  ]
});
formatter.step({
  "line": 13,
  "name": "requesting to categorize Priority of TaskA from High to Low",
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "the Priority of TaskA is categorized as Low",
  "keyword": "Then "
});
formatter.match({
  "location": "ID01CategorizeTasks.requesting_to_categorize_Priority_of_TaskA_from_High_to_Low()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "ID01CategorizeTasks.the_Priority_of_TaskA_is_categorized_as_Low()"
});
formatter.result({
  "status": "skipped"
});
formatter.background({
  "line": 8,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 9,
  "name": "user Ninja has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID01CategorizeTasks.user_ninja_has_the_to_do_list_manager_rest_api_running()"
});
formatter.result({
  "duration": 4021403600,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID01CategorizeTasks.user_ninja_has_the_to_do_list_manager_rest_api_running(ID01CategorizeTasks.java:32)\r\n\tat ✽.Given user Ninja has the to do list manager rest api running(cucumberJava\\ID01_CategorizeTasks.feature:9)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 17,
  "name": "User categorizes tasks (ALternate Flow)",
  "description": "",
  "id": "categorize-task;user-categorizes-tasks-(alternate-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 16,
      "name": "@tag2"
    }
  ]
});
formatter.step({
  "line": 18,
  "name": "requesting to categorize Priority of TaskA from High to High",
  "keyword": "When "
});
formatter.step({
  "line": 19,
  "name": "the Priority of TaskA remains as High",
  "keyword": "Then "
});
formatter.match({
  "location": "ID01CategorizeTasks.requesting_to_categorize_Priority_of_TaskA_from_High_to_High()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "ID01CategorizeTasks.the_Priority_of_TaskA_remains_as_High()"
});
formatter.result({
  "status": "skipped"
});
formatter.background({
  "line": 8,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 9,
  "name": "user Ninja has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID01CategorizeTasks.user_ninja_has_the_to_do_list_manager_rest_api_running()"
});
formatter.result({
  "duration": 4014241900,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID01CategorizeTasks.user_ninja_has_the_to_do_list_manager_rest_api_running(ID01CategorizeTasks.java:32)\r\n\tat ✽.Given user Ninja has the to do list manager rest api running(cucumberJava\\ID01_CategorizeTasks.feature:9)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 22,
  "name": "User attempts to adjust task completion (Error Flow)",
  "description": "",
  "id": "categorize-task;user-attempts-to-adjust-task-completion-(error-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 21,
      "name": "@tag3"
    }
  ]
});
formatter.step({
  "line": 23,
  "name": "requesting to categorize Priority of TaskA from High to invalid priority",
  "keyword": "When "
});
formatter.step({
  "line": 24,
  "name": "an \"404\" error message is issued",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri('cucumberJava\ID02_AddTasktoCourseToDo.feature');
formatter.feature({
  "line": 3,
  "name": "Add Task to Course ToDo list",
  "description": "As a student\nI want to add a task to a course to do list\nSo that I can remember it.",
  "id": "add-task-to-course-todo-list",
  "keyword": "Feature",
  "tags": [
    {
      "line": 2,
      "name": "@tag"
    }
  ]
});
formatter.background({
  "line": 8,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 9,
  "name": "user Lila has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID02AddTasktoCourse.main_page_normal()"
});
formatter.result({
  "duration": 4019008600,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID02AddTasktoCourse.main_page_normal(ID02AddTasktoCourse.java:45)\r\n\tat ✽.Given user Lila has the to do list manager rest api running(cucumberJava\\ID02_AddTasktoCourseToDo.feature:9)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 12,
  "name": "User adds task to ToDo list (Normal Flow)",
  "description": "",
  "id": "add-task-to-course-todo-list;user-adds-task-to-todo-list-(normal-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 11,
      "name": "@tag1"
    }
  ]
});
formatter.step({
  "line": 13,
  "name": "the student requests to create a task under Todos",
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "the task with id \u003cid\u003e, title \u003ctitle\u003e, done_status \u003cdone_status\u003e and description \u003cdescription\u003e should be created",
  "keyword": "Then "
});
formatter.match({
  "location": "ID02AddTasktoCourse.task_todos_post()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "ID02AddTasktoCourse.task_todos_id_get()"
});
formatter.result({
  "status": "skipped"
});
formatter.background({
  "line": 8,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 9,
  "name": "user Lila has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID02AddTasktoCourse.main_page_normal()"
});
formatter.result({
  "duration": 4013995000,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID02AddTasktoCourse.main_page_normal(ID02AddTasktoCourse.java:45)\r\n\tat ✽.Given user Lila has the to do list manager rest api running(cucumberJava\\ID02_AddTasktoCourseToDo.feature:9)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 17,
  "name": "User adds done task to ToDo list (Alternate Flow)",
  "description": "",
  "id": "add-task-to-course-todo-list;user-adds-done-task-to-todo-list-(alternate-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 16,
      "name": "@tag2"
    }
  ]
});
formatter.step({
  "line": 18,
  "name": "the student requests to create a done task under Todos",
  "keyword": "When "
});
formatter.step({
  "line": 19,
  "name": "the task with id \u003cid\u003e, title \u003ctitle\u003e, done_status \u003ctrue\u003e and description \u003cdescription\u003e should be created.",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({
  "location": "ID02AddTasktoCourse.test_todos_id_get()"
});
formatter.result({
  "status": "skipped"
});
formatter.background({
  "line": 8,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 9,
  "name": "user Lila has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID02AddTasktoCourse.main_page_normal()"
});
formatter.result({
  "duration": 4018176300,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID02AddTasktoCourse.main_page_normal(ID02AddTasktoCourse.java:45)\r\n\tat ✽.Given user Lila has the to do list manager rest api running(cucumberJava\\ID02_AddTasktoCourseToDo.feature:9)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 22,
  "name": "User attempts to create a task with an id that already exists (Error Flow)",
  "description": "",
  "id": "add-task-to-course-todo-list;user-attempts-to-create-a-task-with-an-id-that-already-exists-(error-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 21,
      "name": "@tag3"
    }
  ]
});
formatter.step({
  "line": 23,
  "name": "requesting to create a task with id \u003c1\u003e, title \u003ctitle\u003e, done_status \u003cdone_status\u003e and description \u003cdescription\u003e",
  "keyword": "When "
});
formatter.step({
  "line": 24,
  "name": "a \"Error 404\" error message is issued",
  "keyword": "Then "
});
formatter.step({
  "line": 25,
  "name": "the new task is not created",
  "keyword": "And "
});
formatter.match({
  "location": "ID02AddTasktoCourse.task_todos_post_error()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri('cucumberJava\ID03_MarkTaskDone.feature');
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Author: Mahroo"
    }
  ],
  "line": 4,
  "name": "Mark a task as done",
  "description": "\nAs a student\nI mark a task as done on my course to do list\nSo I can track my accomplishments.",
  "id": "mark-a-task-as-done",
  "keyword": "Feature",
  "tags": [
    {
      "line": 3,
      "name": "@tag"
    }
  ]
});
formatter.background({
  "line": 10,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 11,
  "name": "user Mark has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID03MarkTaskDone.to_do_list_manager_rest_api_running()"
});
formatter.result({
  "duration": 4025713100,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID03MarkTaskDone.to_do_list_manager_rest_api_running(ID03MarkTaskDone.java:32)\r\n\tat ✽.Given user Mark has the to do list manager rest api running(cucumberJava\\ID03_MarkTaskDone.feature:11)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 14,
  "name": "User adjusts task completion (Normal Flow)",
  "description": "",
  "id": "mark-a-task-as-done;user-adjusts-task-completion-(normal-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 13,
      "name": "@tag1"
    }
  ]
});
formatter.step({
  "line": 15,
  "name": "requesting to change doneStatus from false to true for a Task with id \"3\"",
  "keyword": "When "
});
formatter.step({
  "line": 16,
  "name": "doneStatus changes from false to true",
  "keyword": "Then "
});
formatter.match({
  "location": "ID03MarkTaskDone.requesting_to_change_doneStatus_from_false_to_true_for_a_Task_with_id_3()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.background({
  "line": 10,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 11,
  "name": "user Mark has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID03MarkTaskDone.to_do_list_manager_rest_api_running()"
});
formatter.result({
  "duration": 4014781600,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID03MarkTaskDone.to_do_list_manager_rest_api_running(ID03MarkTaskDone.java:32)\r\n\tat ✽.Given user Mark has the to do list manager rest api running(cucumberJava\\ID03_MarkTaskDone.feature:11)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 19,
  "name": "User adjusts task completion (ALternate Flow)",
  "description": "",
  "id": "mark-a-task-as-done;user-adjusts-task-completion-(alternate-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 18,
      "name": "@tag2"
    }
  ]
});
formatter.step({
  "line": 20,
  "name": "requesting to change doneStatus from true to true for a Task with id \"3\"",
  "keyword": "When "
});
formatter.step({
  "line": 21,
  "name": "doneStatus remains the same",
  "keyword": "Then "
});
formatter.match({
  "location": "ID03MarkTaskDone.requesting_to_change_doneStatus_from_true_to_true_for_a_Task_with_id_3()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "ID03MarkTaskDone.doneStatus_remains_the_same()"
});
formatter.result({
  "status": "skipped"
});
formatter.background({
  "line": 10,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 11,
  "name": "user Mark has the to do list manager rest api running",
  "keyword": "Given "
});
formatter.match({
  "location": "ID03MarkTaskDone.to_do_list_manager_rest_api_running()"
});
formatter.result({
  "duration": 4020269600,
  "error_message": "java.net.ConnectException: Connection refused: no further information\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl.send(HttpClientImpl.java:563)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientFacade.send(HttpClientFacade.java:119)\r\n\tat cucumberJava.ID03MarkTaskDone.to_do_list_manager_rest_api_running(ID03MarkTaskDone.java:32)\r\n\tat ✽.Given user Mark has the to do list manager rest api running(cucumberJava\\ID03_MarkTaskDone.feature:11)\r\nCaused by: java.net.ConnectException: Connection refused: no further information\r\n\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\r\n\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:589)\r\n\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:839)\r\n\tat java.net.http/jdk.internal.net.http.PlainHttpConnection$ConnectEvent.handle(PlainHttpConnection.java:128)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.handleEvent(HttpClientImpl.java:968)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.lambda$run$3(HttpClientImpl.java:923)\r\n\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1511)\r\n\tat java.net.http/jdk.internal.net.http.HttpClientImpl$SelectorManager.run(HttpClientImpl.java:923)\r\n",
  "status": "failed"
});
formatter.scenario({
  "line": 24,
  "name": "User attempts to adjust task completion (Error Flow)",
  "description": "",
  "id": "mark-a-task-as-done;user-attempts-to-adjust-task-completion-(error-flow)",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 23,
      "name": "@tag3"
    }
  ]
});
formatter.step({
  "line": 25,
  "name": "requesting to mark an UnMarked task as Done for TaskA",
  "keyword": "When "
});
formatter.step({
  "line": 26,
  "name": "an error message is thrown",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({
  "location": "ID03MarkTaskDone.error_message_is_thrown()"
});
formatter.result({
  "status": "skipped"
});
formatter.uri('cucumberJava\ID04_RemoveTask.feature');
formatter.feature({
  "line": 3,
  "name": "remove task from to do list",
  "description": "As a student, I remove an unnecessa