# ECSE429
ECSE 429 project repository

**Group number:** 5

**Group members:** Imane Chafi, Mahroo Rahman, Menglin He and Sofia Dieguez

### Repo structure

ShutdownTest.java : JUnit file for the shutdown command

Tests.java : JUnit files for all of the other endpoints (GET, HEAD, PUT, POST and DELETE for todos, projects, etc.)

Screenshots folder: contains screenshots for charters 1, 2, 3 and 4

### Important notes to get our JUnit code running

Make sure you run the tests with JDK 11 and JUnit 5

Make sure the server is open by running  `java -jar runTodoManagerRestAPI-1.5.5.jar`

You must have the following libraries in the classpath of the project to run the JUnit tests:

- `com.fasterxml.jackson.core.jar` download here: (http://www.java2s.com/Code/Jar/c/Downloadcomfasterxmljacksoncorejar.htm)
- `com.fasterxml.jackson.databind.jar` download here: (http://www.java2s.com/Code/Jar/c/Downloadcomfasterxmljacksondatabindjar.htm)
- `jackson-annotations.2.1.2` download here: (http://www.java2s.com/Code/Jar/j/Downloadjacksonannotations212sourcesjar.htm)
