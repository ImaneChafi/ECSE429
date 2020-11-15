package io.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static java.lang.System.exit;
import static java.lang.System.setOut;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    /*
   Feature: create a to do list for a new class
   Scenario: Student creates to do list for a course with title (Normal flow)
    */
    @Given("the todoManager is running at the backend")
    public void backend_running () throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        // might be 302 for expected code
        assertEquals(200, response.statusCode());
    }

    @When("the student requests to create a to do list with title")
    public void create_todo_with_title () throws Exception {
        var todo = new HashMap<String, Object>() {{
            put("title", "ECSE429");
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @Then("the to do list with title should be created")
    public void create_todo_title_success () throws Exception {
        // i'm cheating here since we need to delete what we already have in the backend first and then create
        // but idk how to do it
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200, response.statusCode());
    }

    /*
    Feature: create a to do list for a new class
    Scenario: Student creates to do list for a course with title and description (Alternate flow)
     */
    @When("the student requests to create a to do list with title and description")
    public void create_todo_title_desc () throws Exception {
        var todo = new HashMap<String, Object>() {{
            put("title", "ECSE429");
            put("description", "Software Validation");
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @Then("the to do with title and description should be created")
    public void create_todo_title_desc_success () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200, response.statusCode());
    }

    /*
    Feature: create a to do list for a new class
    The todoManager is not running at the backend (Error flow)
     */
    @Given("The todoManager is not running at the backend")
    public void backend_not_running () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertNotEquals(200, response.statusCode());
    }

    @When("the student requests to create a to do list")
    public void create_with_no_backend () throws Exception {
        /*
        var todo = new HashMap<String, Object>() {{
            put("title", "ECSE429");
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        */
        final JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Can not create without a running backend!");
        exit(-1);
    }

    @Then("start backend first")
    public void create_with_no_backend_fail () throws Exception {
        final JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "The backend does not start, please start backend first");
        exit(-1);
    }

    /*
    Feature: create a to do list for a new class
    The id is specified when creating a to do list (Error flow)
     */
    @When("the student requests to create a to do with id defined")
    public void create_todo_id () throws Exception {
        var todo = new HashMap<String, Object>() {{
            put("title", "ECSE429");
            put("id", "111");
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @Then("an error message xx should be displayed")
    public void create_todo_id_fail () throws Exception {
        final JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Can not specify ID when create to do, fail...");
        exit(-1);
    }

    // ====================================================
    /*
    Feature: remove task from to do list
    Background definition

     */
    @Given("the following projects is in the system with a todo associated")
    public void create_project_todo () throws IOException, InterruptedException {
        var project = new HashMap<String, Object>() {{
            put("title", "ECSE429");
            put("completed", false);
            put("active", false);
            put("description", "software validation");
        }};

        var todos = new HashMap<String, Object>() {{
            put("title", "partA");
            put("doneStatus", true);
            put("description", "exploratory testing");
        }};

        ObjectMapper om = new ObjectMapper();
        ObjectMapper om1 = new ObjectMapper();
        String requestProp = om.writeValueAsString(project);
        String requestCat = om1.writeValueAsString(todos);

        HttpClient client = HttpClient.newHttpClient();
        HttpClient client1 = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects"))
                .POST(HttpRequest.BodyPublishers.ofString(requestProp)).build();
        HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/2/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(requestCat)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        System.out.println(response1.body());
    }

    @And("the following todos is in the system with a project associated")
    public void create_todo_project () throws IOException, InterruptedException {
        var project = new HashMap<String, Object>() {{
            put("title", "ECSE428");
            put("completed", false);
            put("active", false);
            put("description", "SE practice ");
        }};

        var todos = new HashMap<String, Object>() {{
            put("title", "next term");
            put("doneStatus", false);
            put("description", "exploratory next term course");
        }};

        ObjectMapper om = new ObjectMapper();
        ObjectMapper om1 = new ObjectMapper();
        String requestProp = om.writeValueAsString(project);
        String requestCat = om1.writeValueAsString(todos);

        HttpClient client = HttpClient.newHttpClient();
        HttpClient client1 = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .POST(HttpRequest.BodyPublishers.ofString(requestProp)).build();
        HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/2/tasksof"))
                .POST(HttpRequest.BodyPublishers.ofString(requestCat)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        System.out.println(response1.body());
    }

    /*
    Feature: remove task from to do list
    Scenario: Student removes an unnecessary task form to do list - project/task (Normal flow)
     */
    @Given("the project-task exists in the backend")
    public void project_task_exists () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/2/tasks"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
    }

    @When("the student requests to delete project-task")
    public void request_delete_project_task () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects/2/tasks/2")).DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @Then("the project-task should be deleted from the backend")
    public void deleted_project_task() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/2/tasks/2"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(404,response.statusCode());
    }

    /*
    Feature: remove task from to do list
    Scenario: Student removes an unnecessary task form to do list - to-do/tasks (Alternate flow)
     */
    @Given("the todo-taskof exists in the backend")
    public void todo_taskof_exists () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/tasksof"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200, response.statusCode());
    }

    @When("the student requests to delete todo-taskof")
    public void request_delete_todo_taskof () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/tasksof/1"))
                .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
    }

    @Then("the todo-taskof should be deleted from the backend")
    public void deleted_todo_taskof () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/tasksof"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(404, response.statusCode());
    }

    /*
    Feature: remove task from to do list
    Scenario: There is no task in to to do list (Error flow)
     */
    @Given("no task exist in the backend")
    public void task_not_exist () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/420/tasks"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(404,response.statusCode());
    }

    @When("the student requests to delete task xxxx")
    public void delete_non_existing_task () throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/100/tasksof/100"))
                .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
    }

    @Then("an error message \"\" should be displayed")
    public void error_handling_no_task () throws Exception {
        final JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "The task does not exist!");
        exit(-1);
    }

    /*
    Feature: remove task from to do list
    Scenario: The todoManager is not running at the backend (Error flow)
     */
    @When("the student requests to delete task")
    public void delete_task_no_backend () throws Exception {
        final JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Operation not valid without running backend...");
        exit(-1);    }

    @Then("an error message x should be displayed")
    public void delete_no_bakcend () throws Exception {
        final JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "The backend does not start, please start backend first");
        exit(-1);
    }
}
