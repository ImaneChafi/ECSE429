package cucumberJava; 

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

public class ID10_annotation { 

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
			put("title", "test_todos");
			put("doneStatus", false);
			put("description", "");
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

	@When("^requesting a change of description from OldDescription to NewDescription for TaskA$")
	public void requesting_a_change_of_description_from_olddescription_to_newdescription_for_taska() throws IOException, InterruptedException {

		/////////// Create a taskof ////////////////
		HashMap<String, Object> task = new HashMap<String, Object>() {{
			put("id", "1");
			put("priority", "LOW");
			put("description", "Hi");
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

		/////////// Adjust priority ///////////////
		HashMap<String, String> newtask = new HashMap<String, String>() {{        
			put("id", "1");
			put("priority", "LOW");
			put("description", "Hello");
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

	@When("^requesting a change of description from OldDescription to OldDescription for TaskA$")
	public void requesting_a_change_of_description_from_olddescription_to_olddescription_for_taska() throws IOException, InterruptedException {

		/////////// Create a taskof ////////////////
		HashMap<String, Object> task = new HashMap<String, Object>() {{
			put("id", "1");
			put("priority", "LOW");
			put("description", "Hi");
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

		/////////// Adjust priority ///////////////
		HashMap<String, String> newtask = new HashMap<String, String>() {{        
			put("id", "1");
			put("priority", "LOW");
			put("description", "Hi");
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

	@Then ("^the description of TaskA is NewDescription.$")
	public void the_description_of_taska_is_newdescription() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode()); //TODO: should check to see if description of task changes to Hello
	}

	@Then("^the description of TaskA is OldDescription.$")
	@And("^the description of TaskA is OldDescription.$") //TODO: Is this legal?
	public void the_description_of_taska_is_olddescription() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode()); //TODO: should check to see if description of task remains as Hi
	}

	@Then("^an error message is issued$")
	public void an_error_message_is_issued() throws IOException, InterruptedException {
		////////////// Get task /////////////////
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}

	//	@And("^the description of TaskA is OldDescription.$") //TODO: Is this legal?
	//	public void the_description_of_taska_is_olddescription() throws IOException, InterruptedException {
	//		////////////// Get task /////////////////
	//		HttpClient client = HttpClient.newHttpClient();
	//		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/taskof")).GET().build();
	//		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	//		assertEquals(404,response.statusCode()); //TODO: should check to see if description of task remains as Hi
	//	}
	//}