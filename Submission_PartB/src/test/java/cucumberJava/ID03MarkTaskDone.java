package cucumberJava;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static java.lang.System.exit;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import cucumber.annotation.After;
import cucumber.annotation.en.*;

public class ID03MarkTaskDone { 

	@Given("^user Mark has the to do list manager rest api running$")
	public void to_do_list_manager_rest_api_running() throws IOException, InterruptedException {

		/////////////// Create a new HttpClient ///////////////////
		HttpClient client = HttpClient.newHttpClient();
		// build a new HttpRequest
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567"))
				.GET().build(); // GET is default

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(302, response.statusCode());
	}

	@When("^requesting to change doneStatus from false to true for a Task with id \"3\"$")
	public void requesting_to_change_doneStatus_from_false_to_true_for_a_Task_with_id_3() throws IOException, InterruptedException {

		/////////// Create a taskof ////////////////
		HashMap<String, Object> task = new HashMap<String, Object>() {{
			put("id", "3");
			put("doneStatus", "false");
			put("description", "");
			put("title", "test_todos");
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
		assertEquals(404,response.statusCode());
		assertNotNull(response_str);

		/////////// MARK DONE ///////////////
		var value = new HashMap<String, String>() {{
			put("id", "3");
			put("doneStatus", "true");
			put("description", "");
			put("title", "test_todos");
        }};
        var objectMapper = new ObjectMapper();
        String requestBody_adjust = objectMapper.writeValueAsString(value);
        HttpClient client_adjust = HttpClient.newHttpClient();
        HttpRequest request_adjust = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos/1/taskof"))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody_adjust))
                .build();
        HttpResponse<String> response_adjust = client_adjust.send(request_adjust,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response_adjust.body());
        System.out.println(response_adjust.statusCode());
        assertEquals(404,response_adjust.statusCode());
	}

	@When("^requesting to change doneStatus from true to true for a Task with id \"3\"$")
	public void requesting_to_change_doneStatus_from_true_to_true_for_a_Task_with_id_3() throws IOException, InterruptedException {

		/////////// Create a taskof ////////////////
		HashMap<String, Object> task = new HashMap<String, Object>() {{
			put("id", "3");
			put("doneStatus", "true");
			put("description", "");
			put("title", "test_todos");
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
		assertEquals(404,response.statusCode());
		assertNotNull(response_str);

		/////////// MARK DONE ///////////////
		var value = new HashMap<String, String>() {{
			put("id", "3");
			put("doneStatus", "true");
			put("description", "");
			put("title", "test_todos");
        }};
        var objectMapper = new ObjectMapper();
        String requestBody_adjust = objectMapper.writeValueAsString(value);
        HttpClient client_adjust = HttpClient.newHttpClient();
        HttpRequest request_adjust = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos/1/taskof"))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody_adjust))
                .build();
        HttpResponse<String> response_adjust = client_adjust.send(request_adjust,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response_adjust.body());
        System.out.println(response_adjust.statusCode());
        assertEquals(404,response_adjust.statusCode());
	}
	
	@Then("^doneStatus remains the same$")
	public void doneStatus_remains_the_same() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode()); //TODO: should check to see if priority of task changes to LOW
	}
	
	@Then ("^doneStatus_changes_from_false_to_true$")
	public void doneStatus_changes_from_false_to_true() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode()); //TODO: should check to see if priority of task chanfes to MEDIUM
	}
	

	@Then("^an error message is thrown$")
	public void error_message_is_thrown() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}

}
