package cucumberJava;

import static java.lang.System.exit;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cucumber.annotation.After;
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
public class ID07QueryIncompleteTasks {

	//Scenario 1 - 	Student requests list of all incomplete tasks (Normal Flow)
	   @Given("^student \"Boba Fett\" has the to do list manager rest api running$") 
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
		
	   @When("^the student requests a list of incomplete tasks$") 
	   public void test_query() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos?doneStatus=false")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(200,response.statusCode());
	        System.out.println(response.body());
	    }
		
	   @Then("^the following list of tasks is generated$") 
	   public void test_query_generated() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos?doneStatus=false")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(200,response.statusCode());
	        System.out.println(response.body());
	        
	   }
		 //Scenario 2 - Student requests list of completed tasks (Alternate Flow)
	   @Given("^student \"Freddy Mercury\" has the to do list manager rest api running $") 
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
		
	   @When("^the student requests a list of tasks with doneStatus <true> $") 
	   public void test_query_true() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos?doneStatus=true")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(200,response.statusCode());
	        System.out.println(response.body());
	        
	   }
		
	   @Then("^the list of done tasks is generated$") 
	   public void test_query_done() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos?doneStatus=true")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(200,response.statusCode());
	        System.out.println(response.body());      
	   }
	   
	  //Scenario 3 - User attempts to query for unexistant task status (Error Flow)
	   @Given("^user Diana has the to do list manager rest api running$") 
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
		
	   @When("^the student requests a list of tasks with doneStatus <maybe>$") 
	   public void test_query_maybe() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos?doneStatus=maybe")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());    
	        System.out.println(response.body());
	   }
		
	   @Then("no task show up$") 
	   public void test_query_maybe_nonexistent() throws IOException, InterruptedException {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("http://localhost:4567/todos?doneStatus=maybe")).GET()
	                .build();
	        HttpResponse<Void> response = client.send(request,
	                HttpResponse.BodyHandlers.discarding());
	        assertEquals(200,response.statusCode());
	        System.out.println(response.body());      
	   }
	  
	   //No after since only query

}
