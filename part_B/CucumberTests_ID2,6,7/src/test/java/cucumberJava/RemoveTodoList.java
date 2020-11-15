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
public class RemoveTodoList {

	//Scenario 1 - 	User removes ToDo list (Normal Flow)
	   @Given("^user Lila has the to do list manager rest api running  .$") 
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
		
	   @When("^the student requests to delete a todo list task using id <id>$") 
	   public void test_todo_id_delete() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1")).DELETE()
	                .build();
	        HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
	        System.out.println(response.body());
	        assertEquals(404,response.statusCode());
	    }
		
	   @Then("^the task with id <id>, title <title>, done_status <done_status> and description <description> should be deleted$") 
	   public void task_todos_id_get() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/1")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(404,response.statusCode());
	        System.out.println(response.body());
	        //print the todos? assert todo exists
	    }
		 //Scenario 2 - User removes ToDo list without description (Alternate Flow)
	   @Given("^user Lila has the to do list manager rest api running .$") 
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
		
	   @When("^the student requests to delete a todo list with description <> using id <id> $") 
	   public void todo_id_delete_alternate() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/2")).DELETE()
	                .build();
	        HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
	        System.out.println(response.body());
	        assertEquals(200,response.statusCode());
	    }
		
	   @Then("^the todo with id <id>, title <title>, done_status <done_status> and description <> should be deleted.$") 
	   public void test_todos_id_get() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/2")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(404,response.statusCode());
	        System.out.println(response.body());
	        //print the todos? assert todo taskof exists
	    }
	   
	  //Scenario 3 - User attempts to delete a todo with an unexistant id (Error Flow)
	   @Given("^user Lila has the to do list manager rest api  running  $") 
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
		
	   @When("^requesting to delete a task with id <20>, title <title>, done_status <done_status> and description <description>$") 
	   public void todo_id_delete_error() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/20")).DELETE()
	                .build();
	        HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
	        System.out.println(response.body());
	        assertEquals(404,response.statusCode());
	    }
		
	   @Then("a \"Error 404\" error message is issued.$") 
	   public void test_todos_id_get_error() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos/20")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(404,response.statusCode());
	        System.out.println(response.body());
	        //print the todos? assert todo taskof exists
	    }


}
