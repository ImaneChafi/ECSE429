                  package cucumberJava; 

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static java.lang.System.exit;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import cucumber.annotation.en.*;

public class ID01_annotation { 

	@Given("^user Fizbin has the to do list manager rest api running$")
	public void user_fizbin_has_the_to_do_list_manager_rest_api_running() throws IOException, InterruptedException {

		/////////////// Create a new HttpClient ///////////////////
		HttpClient client = HttpClient.newHttpClient();
		// build a new HttpRequest
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567"))
				.GET().build(); // GET is default

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(302, response.statusCode());

		/////////////// Create a todo //////////////////
		HashMap<String, Object> value = new HashMap<String, Object>() {{
			put("id", "3");
			put("doneStatus", false);
			put("description", "");
			put("title", "test_todos");
			put("priority", "High");
			
			
		}};
		//what should the url be?
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

	@When("^requesting to categorize Priority of TaskA from High to Low$")
	public void requesting_to_categorize_Priority_of_TaskA_from_High_to_Low() throws IOException, InterruptedException {

		/////////// Create a taskof ////////////////
		HashMap<String, Object> task = new HashMap<String, Object>() {{
			put("id", "3");
			put("doneStatus", false);
			put("description", "");
			put("title", "test_todos");
			put("priority", "High");
		}};
		ObjectMapper om = new ObjectMapper();
		String requestBody = om.writeValueAsString(task);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/taskof"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(201,response.statusCode());
		assertNotNull(response_str);

		/////////// Priority set ///////////////
		HashMap<String, String> newtask = new HashMap<String, String>() {{        
			put("id", "3");
			put("doneStatus", true);
			put("description", "");
			put("title", "test_todos");
			put("priority", "Low");
		}};
		ObjectMapper objectMapper = new ObjectMapper();
		String requestNewBody = objectMapper.writeValueAsString(newtask);
		HttpClient newclient = HttpClient.newHttpClient();
		HttpRequest newrequest = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/taskof")
						.PUT(HttpRequest.BodyPublishers.ofString(requestNewBody))
						.build();
				HttpResponse<String> newresponse = newclient.send(newrequest, HttpResponse.BodyHandlers.ofString());
				System.out.println(newresponse.body());
				System.out.println(newresponse.statusCode());
				assertEquals(400,newresponse.statusCode());
	}

	@When("^requesting to categorize Priority of TaskA from High to High$")
	public void requesting_to_categorize_Priority_of_TaskA_from_High_to_High() throws throws IOException, InterruptedException {

		/////////// Create a taskof ////////////////
		HashMap<String, Object> task = new HashMap<String, Object>() {{
			put("id", "3");
			put("doneStatus", true);
			put("description", "");
			put("title", "test_todos");
			put("priority", "High");
		}};
		ObjectMapper om = new ObjectMapper();
		String requestBody = om.writeValueAsString(task);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/taskof"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(201,response.statusCode());
		assertNotNull(response_str);

		/////////// Priority unchanged ///////////////
		HashMap<String, String> newtask = new HashMap<String, String>() {{        
			put("id", "3");
			put("doneStatus", true);
			put("description", "");
			put("title", "test_todos");
			put("priority", "High");
		}};
		ObjectMapper objectMapper = new ObjectMapper();
		String requestNewBody = objectMapper.writeValueAsString(newtask);
		HttpClient newclient = HttpClient.newHttpClient();
		HttpRequest newrequest = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/taskof")
						.PUT(HttpRequest.BodyPublishers.ofString(requestNewBody))
						.build();
				HttpResponse<String> newresponse = newclient.send(newrequest, HttpResponse.BodyHandlers.ofString());
				System.out.println(newresponse.body());
				System.out.println(newresponse.statusCode());
				assertEquals(400,newresponse.statusCode());
	}
	
	@Then ("^the Priority of TaskA is categorized as Low$")
	public void the_Priority_of_TaskA_is_categorized_as_Low() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode()); //TODO: should check to see if priority of task chanfes to MEDIUM
	}
	
	@Then("^the Priority of TaskA remains as High$")
	public void the_Priority_of_TaskA_remains_as_High() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode()); //TODO: should check to see if priority of task changes to LOW
	}
	
	@Then("^an error message is issued$")
	public void an_error_message_is_issued() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     