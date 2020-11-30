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
public class Todos_Performance_Test {





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
			assertEquals(200, response.statusCode());
		} catch (IOException e) {
			final JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Start Server on Terminal.");
			exit(-1);
		}
		
		 
	}





	// POST /todos
	/* Notes: This test sees if the post api succeeds*/
	@Test
	public void test_todos_post() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();

		var value = new HashMap<String, Object>() {{
			put("title", "test_todos");
			put("doneStatus", false);
			put("description", "");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(201,response.statusCode());
		assertNotNull(response_str);


		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_post: " + T1);
		System.out.print("T2 for test_todos_post: " + T2);

	}


	// POST /todos/:id
	/* Notes: This test checks if the POST API doesn't work if the id is present, as seen in exploratory testing*/
	@Test
	public void test_todos_id_post() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("id", "9");
			put("title", "test_todos_id_post");
			put("doneStatus", "false");
			put("description", "testing todos with ID POST");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(404,response.statusCode());
		String error = "{\"errorMessages\":[\"No such todo entity instance with GUID or ID $value.id found\"]}";
		assertEquals(error, response_str.body());


		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_id_post: " + T1);
		System.out.print("T2 for test_todos_id_post: " + T2);

	}

	

	// POST /todos/:id
	/* Notes: This test checks if the POST API works if the id is not present, as seen in exploratory testing*/
	@Test
	public void test_todos_id_post_works() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("title", "test_todos_id_post");
			put("doneStatus", "false");
			put("description", "testing todos with ID POST");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(404,response.statusCode());


		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_id_post_works: " + T1);
		System.out.print("T2 for test_todos_id_post_works: " + T2);

	}

	// PUT /todos/:id
	/*Notes: This test checks if the PUT api doesn't work with ID, as seen in exploratory testing*/
	@Test
	public void test_todos_id_put() throws IOException, InterruptedException{
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("id","1");
			put("title","Test todos id PUT");
			put("doneStatus","false");
			put("description","testing todos with ID PUT");
		}};
		long start_t2 = System.nanoTime();
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(404,response.statusCode());


		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_id_put: " + T1);
		System.out.print("T2 for test_todos_id_put: " + T2);


	}

	// PUT /todos/:id
	/*Notes: This test checks if the PUT api works without ID, as seen in exploratory testing*/
	@Test
	public void test_todos_id_put_works() throws IOException, InterruptedException{
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("title","Test todos id PUT");
			put("doneStatus","false");
			put("description","testing todos with ID PUT");
		}};
		long start_t2 = System.nanoTime();
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/$value.id"))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(404,response.statusCode());


		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_id_put_works: " + T1);
		System.out.print("T2 for test_todos_id_put_works: " + T2);


	}

	// DELETE /todos/:id
	/* Notes: This function sees if the delete doesn't work if the id of the todo doesn't exist, and passes if the delete doesn't work, as seen in exploratory testing*/
	@Test
	public void test_todo_id_delete() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/3")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		assertEquals(404,response.statusCode());

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todo_id_delete: " + T1);
		System.out.print("T2 for test_todo_id_delete: " + T2);

	}
	


	// POST /todos/:id/categories
	/* Notes: This test checks if the POST API indeed works, as seen in the exploratory testing. */
	@Test
	public void test_todos_categories_post() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();

		var value = new HashMap<String, String>() {{
			put("id", "3");
			put("title", "test_todos_categories_post");
			put("description", "");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		assertEquals(201,response.statusCode());

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_categories_post: " + T1);
		System.out.print("T2 for test_todos_categories_post: " + T2);

	}

	// POST /todos/:id/categories
	/* Notes: This test checks if the POST API indeed works without ids, as seen in the exploratory testing. */
	@Test
	public void test_todos_categories_post_works() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("title", "test_todos_categories_post");
			put("description", "");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		assertEquals(201,response.statusCode());


		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_categories_post_works: " + T1);
		System.out.print("T2 for test_todos_categories_post_works: " + T2);

	}

	//////////////////////////////////4. /todos/:id/categories/:id///////////////////////////////////

	//DELETE /todos/:id/categories/:id
	/*Notes: Could not find any instances with todos/1/categories/0
from exploratory testing, cannot find such an instance, thus the expected code is 404
	 */
	@Test
	public void test_delete_todo_category_with_id_f() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/categoties/0")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		assertEquals(404,response.statusCode());

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_delete_todo_category_with_id_f: " + T1);
		System.out.print("T2 for test_delete_todo_category_with_id_f: " + T2);

	}

	
	// POST /todos/:id/tasksof
	/* Notes: This test checks if the POST api doesnt work with ID, as seen in the exploratory testing*/
	@Test
	public void test_todos_id_tasksof_post() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("id", "1");
			put("title", "Office Work");
			put("completed", "false");
			put("active", "false");
			put("tasks", "3");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/tasksof"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		assertEquals(404,response.statusCode());

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_id_tasksof_post: " + T1);
		System.out.print("T2 for test_todos_id_tasksof_post: " + T2);

	}
	//////////////////////////////////6. /todos/:id/tasksof/:id///////////////////////////////////

	// DELETE /todos/:id/tasksof/:id
	/*Notes: This test is to see if the delete with an unexistant task, as seen in exploratory testing, returns a 404 error*/
	@Test
	public void test_todo_id_tasksof_delete() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/1/tasksof/1")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		assertEquals(404,response.statusCode());


		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todo_id_tasksof_delete: " + T1);
		System.out.print("T2 for test_todo_id_tasksof_delete: " + T2);

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
