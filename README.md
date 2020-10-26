# ECSE429
ECSE 429 project repo!!

### Repo structure

ShutdownTest.java : JUnit file for the shutdown command

Tests.java : JUnit files for all of the other endpoints (GET, HEAD, PUT, POST and DELETE for todos, projects, etc.)

Screenshots folder: contains screenshots for charters 1, 2, 3 and 4

### Important notes to get our JUnit code running

Make sure you run the tests with JDK 11 and JUnit 5

Make sure the server is open by running  `java -jar runTodoManagerRestAPI-1.5.5.jar`

You must have the following libraries:
- `com.fasterxml.jackson.core.jar`
- `com.fasterxml.jackson.databind.jar`
- `jackson-annotations.2.1.2`

### Other
#### Junits made by the creater of the app

https://github.com/eviltester/thingifier/blob/master/todoManagerRestAuto/src/test/java/uk/co/compendiumdev/thingifier/tactical/postmanreplication/TodosCrudTest.java

#### Tutorial on how to use the Java Http Client

http://zetcode.com/java/httpclient/

#### Tutorial on how to make Unit tests on Java Http Client

https://www.baeldung.com/integration-testing-a-rest-api  
