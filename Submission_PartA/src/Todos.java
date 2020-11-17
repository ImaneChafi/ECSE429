import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
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

public class Todos {

	//////////////////See if server has started////////////////////////////////////////////////////

	@Before
	public void main_page() throws IOException, InterruptedException {
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
			exit(-1);
		}
	}
	
	//////////////////////////////////Test Queries////////////////////////////////////

	// GET /todos/query
	/* Notes: This test checks if the GET QUERY for todos with an id doesn't work, as seen in exploratory testing*/
	@Test
	public void test_todos_query() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos?doneStatus=false")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());//Added printing the response to know what was changed
	}

	/////////////////////////1./todos//////////////////////////////////////////////////////////

	// HEAD /todos/:id/categories
	/* Notes: This test checks if the HEAD API works */
	@Test
	public void test_todos_categories_head() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/todos/1/categories"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(200,response.statusCode());
	}

	//HEAD /todos
	/* Notes: This test sees if the head api succeeds */
	@Test
	public void test_todos_head() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/todos"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(200,response.statusCode());
	}

	// POST /todos
	/* Notes: This test sees if the post api succeeds*/
	@Test
	public void test_todos_post() throws IOException, InterruptedException {

		var value = new HashMap<String, Object>() {{
			put("title", "test_todos");
			put("doneStatus", false);
			put("description", "");
		}};

		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(201,response.statusCode());
		assertNotNull(response_str);
	}

	//////////////////////////////////2. /todos/:id////////////////////////////////////

	// GET /todos/:id
	/* Notes: This test checks if the GET for todos with an id doesn't work, as seen in exploratory testing*/
	@Test
	public void test_todos_id_get() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());
	}

	// HEAD /todos/:id
	/* Notes: This test checks if the HEAD api doesn't work, as seen in exploratory testing. */
	@Test
	public void test_todos_id_head() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/todos/1"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(200,response.statusCode());
	}

	// POST /todos/:id
	/* Notes: This test checks if the POST API doesn't work if the id is present, as seen in exploratory testing*/
	@Test
	public void test_todos_id_post() throws IOException, InterruptedException {

		var value = new HashMap<String, String>() {{
			put("id", "9");
			put("title", "test_todos_id_post");
			put("doneStatus", "false");
			put("description", "testing todos with ID POST");
		}};

		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
		String error = "{\"errorMessages\":[\"No such todo entity instance with GUID or ID $value.id found\"]}";
		assertEquals(error, response_str.body());
	}

	// GET /todos
	/* Notes: This test sees if the get api succeeds*/
	@Test
	public void test_todos_get() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());
	}

	// POST /todos/:id
	/* Notes: This test checks if the POST API works if the id is not present, as seen in exploratory testing*/
	@Test
	public void test_todos_id_post_works() throws IOException, InterruptedException {

		var value = new HashMap<String, String>() {{
			put("title", "test_todos_id_post");
			put("doneStatus", "false");
			put("description", "testing todos with ID POST");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}

	// PUT /todos/:id
	/*Notes: This test checks if the PUT api doesn't work with ID, as seen in exploratory testing*/
	@Test
	public void test_todos_id_put() throws IOException, InterruptedException{
		var value = new HashMap<String, String>() {{
			put("id","1");
			put("title","Test todos id PUT");
			put("doneStatus","false");
			put("description","testing todos with ID PUT");
		}};
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(409,response.statusCode());

	}

	// PUT /todos/:id
	/*Notes: This test checks if the PUT api works without ID, as seen in exploratory testing*/
	@Test
	public void test_todos_id_put_works() throws IOException, InterruptedException{
		var value = new HashMap<String, String>() {{
			put("title","Test todos id PUT");
			put("doneStatus","false");
			put("description","testing todos with ID PUT");
		}};
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(404,response.statusCode());

	}

	// DELETE /todos/:id
	/* Notes: This function sees if the delete doesn't work if the id of the todo doesn't exist, and passes if the delete doesn't work, as seen in exploratory testing*/
	@Test
	public void test_todo_id_delete() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/3")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(404,response.statusCode());
	}
	//////////////////////////////////3. /todos/:id/categories////////////////////////////////////

	// GET /todos/:id/categories
	/* Notes: This test checks if the GET api for todos and categories works*/
	@Test
	public void test_todos_categories_get() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/categories")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());
	}



	// POST /todos/:id/categories
	/* Notes: This test checks if the POST API indeed works, as seen in the exploratory testing. */
	@Test
	public void test_todos_categories_post() throws IOException, InterruptedException {
		var value = new HashMap<String, String>() {{
			put("id", "3");
			put("title", "test_todos_categories_post");
			put("description", "");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(201,response.statusCode());
	}

	// POST /todos/:id/categories
	/* Notes: This test checks if the POST API indeed works without ids, as seen in the exploratory testing. */
	@Test
	public void test_todos_categories_post_works() throws IOException, InterruptedException {
		var value = new HashMap<String, String>() {{
			put("title", "test_todos_categories_post");
			put("description", "");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(201,response.statusCode());
	}

	//////////////////////////////////4. /todos/:id/categories/:id///////////////////////////////////

	//DELETE /todos/:id/categories/:id
	/*Notes: Could not find any instances with todos/1/categories/0
from exploratory testing, cannot find such an instance, thus the expected code is 404
	 */
	@Test
	public void test_delete_todo_category_with_id_f() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/categoties/0")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		assertEquals(404,response.statusCode());
	}

	//////////////////////////////////5. /todos/:id/tasksof///////////////////////////////////

	// GET /todos/:id/tasksof
	/* Notes: This test checks if the GET api for todos and taskof works*/
	@Test
	public void test_todos_tasksof_get() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/tasksof")).GET()
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		System.out.println(response.body());
		//assertNotNull(response.body());
	}

	// HEAD /todos/:id/tasksof
	/* Notes: This test checks if the HEAD api for todos with taskof works*/
	@Test
	public void test_todos_id_tasksof_head() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/todos/1/tasksof"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(200,response.statusCode());
	}

	// POST /todos/:id/tasksof
	/* Notes: This test checks if the POST api doesnt work with ID, as seen in the exploratory testing*/
	@Test
	public void test_todos_id_tasksof_post() throws IOException, InterruptedException {
		var value = new HashMap<String, String>() {{
			put("id", "1");
			put("title", "Office Work");
			put("completed", "false");
			put("active", "false");
			put("tasks", "3");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/tasksof"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(400,response.statusCode());
	}
	//////////////////////////////////6. /todos/:id/tasksof/:id///////////////////////////////////

	// DELETE /todos/:id/tasksof/:id
	/*Notes: This test is to see if the delete with an unexistant task, as seen in exploratory testing, returns a 404 error*/
	@Test
	public void test_todo_id_tasksof_delete() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/tasksof/1")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(404,response.statusCode());
	}

	 @After
	   public void taskof_todo_id_delete() throws IOException, InterruptedException {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:4567/todos/1/tasksof/1")).DELETE()
					.build();
			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			assertEquals(404,response.statusCode());
		}
}
