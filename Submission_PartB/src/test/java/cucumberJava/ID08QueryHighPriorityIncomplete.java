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
public class ID08QueryHighPriorityIncomplete {

	//Scenario 1 - 	Student requests list of all incomplete high priority tasks (Normal Flow)
	@Given("^student \"Marcus\" has the to do list manager rest api running$") 
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

	@When("^the student requests a list of incomplete high priority tasks$") 
	public void test_query() throws IOException, InterruptedException {
		
	/////////////// Create a todo //////////////////
		HashMap<String, Object> value = new HashMap<String, Object>() {{
			put("title", "test_todos");
			put("doneStatus", false);
			put("description", "");
			put("Priority", "High");
		}};
		ObjectMapper om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
	
		HttpClient todoclient = HttpClient.newHttpClient();
		HttpRequest todorequest = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
	
		HttpResponse<String> response_str = todoclient.send(todorequest, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> todoresponse = todoclient.send(todorequest, HttpResponse.BodyHandlers.discarding());
		assertEquals(400,todoresponse.statusCode());
		assertNotNull(response_str);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos?doneStatus=false&priority=High")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());
	}

	@Then("^the list of incomplete tasks has been generated$") 
	public void test_query_generated() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos?doneStatus=false&priority=High")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());

	}
	//Scenario 2 - Student requests list of completed high priority tasks (Alternate Flow)
	@Given("^student \"Mercury\" has the to do list manager rest api running $") 
	public void main_page_alternate() throws IOException, InterruptedException {
		// create a new HttpClient
		HttpClient client = HttpClient.newHttpClient();
		// build a new HttpRequest
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567"))
				.GET().build(); // GET is default

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(302, response.statusCode());
		/////////////// Create a todo //////////////////
		HashMap<String, Object> value = new HashMap<String, Object>() {{
			put("title", "test_todos");
			put("doneStatus", false);
			put("description", "");
			put("Priority", "High");
		}};
		ObjectMapper om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);

		HttpClient todoclient = HttpClient.newHttpClient();
		HttpRequest todorequest = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = todoclient.send(todorequest, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> todoresponse = todoclient.send(todorequest, HttpResponse.BodyHandlers.discarding());
		assertEquals(201,todoresponse.statusCode());
		assertNotNull(response_str);


	}

	@When("^the student requests a list of tasks with doneStatus <true> for High priority tasks$") 
	public void test_query_high_priority_true() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos?doneStatus=true&priority=High")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());

	}

	@Then("^the list of tasks with completed high priorities is created$") 
	public void test_query_high_priority_done() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos?doneStatus=true&priority=High")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());      
	}

	//Scenario 3 - User attempts to query for unexistant task status (Error Flow)
	@Given("^user \"Ned\" has the to do list manager rest api running $") 
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

	@When("^Ned requests a list of tasks with doneStatus <maybe>$") 
	public void test_query_maybe() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos?doneStatus=maybe&priority=High")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());    
	}

	@Then("there is no task that show up$") 
	public void test_query_maybe_nonexistent() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos?doneStatus=maybe&priority=High")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());      
	}
	
	//No After as only query, the before was changed to a Given as Background


}
