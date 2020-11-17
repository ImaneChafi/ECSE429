package cucumberJava;

import com.fasterxml.jackson.databind.ObjectMapper;
import static java.lang.System.exit;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cucumber.annotation.en.Given; 
import cucumber.annotation.en.Then; 
import cucumber.annotation.en.When;
import cucumber.annotation.After;
import cucumber.annotation.en.And;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.*;
public class ID05CreateTodoList {
	

    /*
   Feature: create a to do list for a new class
   Scenario: Student creates to do list for a course with title (Normal flow)
    */
    @Given("the todoManager is running at the backend")
    public void backend_running () throws IOException, InterruptedException {
    	try {
            // create a new HttpClient
            HttpClient client = HttpClient.newHttpClient();
            // build a new HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:4567"))
                    .GET().build(); // GET is default
            
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            assertEquals(302, response.statusCode());
        } catch (IOException e) {
            final JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Start Server on Terminal.");
            //exit(-1);
        }
    }

    @When("the student requests to create a to do list with title")
    public void create_todo_with_title () throws IOException, InterruptedException {
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
    public void create_todo_title_success () throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        boolean result = response.body().contains("ECSE429");
        assertTrue(result);
    }

    /*
    Feature: create a to do list for a new class
    Scenario: Student creates to do list for a course with title and description (Alternate flow)
     */
    @When("the student requests to create a to do list with title and description")
    public void create_todo_title_desc () throws IOException, InterruptedException {
        var todo = new HashMap<String, Object>() {{
            put("title", "ECSE428");
            put("description", "Software Engineering Practice");
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
    public void create_todo_title_desc_success () throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        boolean result = response.body().contains("Software Engineering Practice");
        assertTrue(result);
    }

    /*
    Feature: create a to do list for a new class
    The todoManager is not running at the backend (Error flow)
     */
    @Given("The todoManager is running at the backend")
    public void backend_not_running () throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(302, response.statusCode());
    }
    
    @When("the student requests to create a to do with id defined")
    public void create_todo_id () throws IOException, InterruptedException {
        var todo = new HashMap<String, Object>() {{
            put("title", "not-gonna-work");
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

    @Then("cannot create with id")
    public void create_todo_id_fail () throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        boolean result = response.body().contains("not-gonna-work");
        
        if (!result) {
            final JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Can not specify ID when create to do, fail...");
        }
    }
    

}
