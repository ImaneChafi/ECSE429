package cucumberJava;

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
public class AddTasktoCourse {

	//Scenario 1 - 	User adds task to ToDo list (Normal Flow)
	   @Given("^user Lila has the to do list manager rest api running$") 
	   public void main_page_normal() throws IOException, InterruptedException {
	            // create a new HttpClient
	            HttpClient client = HttpClient.newHttpClient();
	            // build a new HttpRequest
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create("http://localhost:4567"))
	                    .GET().build(); // GET is default
	            
	            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	            assertEquals(302, response.statusCode());
	        
	    }
		
	   @When("^the student requests to create a task under Todos$") 
	    public void task_todos_post() throws IOException, InterruptedException {

	        var value = new HashMap<String, Object>() {{
	            put("title", "newTasktodos");
	            put("doneStatus", false);
	            put("description", "description");
	        }};

	        var om = new ObjectMapper();
	        String requestBody = om.writeValueAsString(value);

	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1/tasksof"))
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();

	        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
	        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	        assertEquals(400,response.statusCode());
	        assertNotNull(response_str);
	    }
		
	   @Then("^the task with id <id>, title <title>, done_status <done_status> and description <description> should be created$") 
	   public void task_todos_id_get() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(200,response.statusCode());
	        System.out.println(response.body());
	        //print the todos? assert todo taskof exists
	    }
		 //Scenario 2 - User adds done task to ToDo list (Alternate Flow)
	   @Given("^user Lila has the to do list manager rest api running $") 
	   public void main_page_alternate() throws IOException, InterruptedException {
	            // create a new HttpClient
	            HttpClient client = HttpClient.newHttpClient();
	            // build a new HttpRequest
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create("http://localhost:4567"))
	                    .GET().build(); // GET is default
	            
	            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	            assertEquals(302, response.statusCode());
	        
	    }
		
	   @When("^the student requests to create a done task under Todos $") 
	    public void test_todos_post() throws IOException, InterruptedException {

	        var value = new HashMap<String, Object>() {{
	            put("title", "alternateTest");
	            put("doneStatus", true);
	            put("description", "test_task");
	        }};

	        var om = new ObjectMapper();
	        String requestBody = om.writeValueAsString(value);

	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1/tasksof"))
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();

	        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
	        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	        assertEquals(400,response.statusCode());
	        assertNotNull(response_str);
	    }
		
	   @Then("^the task with id <id>, title <title>, done_status <true> and description <description> should be created.$") 
	   public void test_todos_id_get() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(200,response.statusCode());
	        System.out.println(response.body());
	        //print the todos? assert todo taskof exists
	    }
	   
	  //Scenario 3 - User attempts to create a task with an id that already exists (Error Flow)
	   @Given("^user Lila has the to do list manager rest api running.$") 
	   public void main_page_error() throws IOException, InterruptedException {
	            // create a new HttpClient
	            HttpClient client = HttpClient.newHttpClient();
	            // build a new HttpRequest
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create("http://localhost:4567"))
	                    .GET().build(); // GET is default
	            
	            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	            assertEquals(302, response.statusCode());
	        
	    }
		
	   @When("^requesting to create a task with id <1>, title <title>, done_status <done_status> and description <description>$") 
	    public void task_todos_post_error() throws IOException, InterruptedException {

	        var value = new HashMap<String, Object>() {{
	            put("title", "newTasktodos");
	            put("doneStatus", true);
	            put("description", "description");
	        }};

	        var om = new ObjectMapper();
	        String requestBody = om.writeValueAsString(value);

	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1/tasksof"))
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();
	    }
		
	   @Then("^a \"Error 400\" error message is issued$") 
	   public void test_todos_id_get_error() throws IOException, InterruptedException {
		   var value = new HashMap<String, Object>() {{
	            put("title", "newTasktodos");
	            put("doneStatus", true);
	            put("description", "description");
	        }};

	        var om = new ObjectMapper();
	        String requestBody = om.writeValueAsString(value);

	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1/tasksof"))
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();

	        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
	        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	        assertEquals(400,response.statusCode());
	        assertNotNull(response_str);
	    }


}
