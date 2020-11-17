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

public class Categories {

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
	// GET /categories/:id
	@Test
	public void test_get_categories_with_id() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}

	// HEAD /categories/:id
	@Test
	public void test_cat_id_head() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/categories/1"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(404,response.statusCode());
	}

	// POST /categories/:id
	@Test
	public void test_post_categories_with_id() throws IOException, InterruptedException{
		var values = new HashMap<String, String>() {{  
			put("description","HELLO");
			put("id","2");
			put("title","Office");
		}};
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(values);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/2"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(400,response.statusCode());
	}

	// PUT /categories/:id
	@Test
	public void test_put_categories_with_id() throws IOException, InterruptedException{
		var values = new HashMap<String, String>() {{        
			put("description","HELLO");
			put("id","2");
			put("title","Office");
		}};
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(values);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/2"))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(400,response.statusCode());
	}

	// DELETE /categories/:id
	@Test
	public void test_del_project_cat_id() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1"))
				.DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		assertEquals(404,response.statusCode());
	}
	//////////////////////////////////13. /categories/:id/projects //////////////////////////////////

	/*
	    Determine the status of a web page when accessing categories with ids and categories
	 */
	@Test
	public void categories_ids() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1"))
				.GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}

	// GET /categories/:id/projects
	@Test
	public void test_get_categories_with_id_projects() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1/projects")).GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}

	// HEAD /categories/:id/projects
	@Test
	public void test_cat_id_head_projects() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/categories/1/projects"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(200,response.statusCode());
	}

	// POST /categories/:id/projects with predefined id returns 400 error
	@Test
	public void test_post_categories_with_id_projects() throws IOException, InterruptedException{
		var values = new HashMap<String, String>() {{  
			put("description","HELLO");
			put("id","2");
			put("title","Office");
		}};
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(values);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/2/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		System.out.println(response.statusCode());
		//assertEquals(404,response.statusCode());
	}

	//////////////////////////////////14. /categories/:id/projects/:id //////////////////////////////////



	// DELETE /categories/:id/projects/:id
	/*Notes: Cannot find any instances with different id  categories/1/projects/1
	    from exploratory testing, cannot find such an instance, thus the expected code is 404
	 */
	@Test
	public void test_delete_project_with_ids_category_with_id_project() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1/projects/1")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		assertEquals(404,response.statusCode());
	}

	//////////////////////////////////15. /categories/:id/todos//////////////////////////////////

	
	// GET /categories/:id/todos/
	/*Notes: based on the tests run from exploratory testing, this should return a 404 error as adding even a / breaks the frontend
	 */
	@Test
	public void test_category_id_todos_fail_get() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1/todos/")).GET()
				.build();
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode()); //this test should receive a 404, as the application's frontend is crashing
		assertNotNull(response_str);
	}

	// HEAD /categories/:id/todos
	/*Notes: headers for the todo items related to category, with given id, by the relationship named todos*/
	@Test
	public void test_categories_ids_todos_head_pass() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/categories/1/todos"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}

	// HEAD /categories/:id/todos
	/* Notes: Returns 404 error, we expect the 404 error on wrong endpoint with HEAD by adding / at the end
		headers for the todo items related to category, with given id, by the relationship named todos*/
	@Test
	public void test_categories_ids_todos_head_fail() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/categories/1/todos/"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}

	// POST /categories/:id/todos   
	/* Notes: succeeds if id is not present, but documentation doesn't say that you should'nt add an id. 
	  	create an instance of a relationship named categories between todos 
	  	Works if ID doesn't exist in parameters 
	 */
	@Test
	public void test_categories_todos_post_works() throws IOException, InterruptedException {
		var value = new HashMap<String, String>() {{
			put("title", "test1"); //This one is wrong
			put("description", "hello world");
		}};

		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1/todos"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
	}
	
	// GET /categories/:id/todos
		/*Notes: success
		    return all the categories items related to category and todos, with given id, by the relationship named todos
		 */
		@Test
		public void test_category_id_todos_get() throws IOException, InterruptedException {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:4567/categories/1/todos")).GET()
					.build();
			HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
			HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
			assertEquals(200,response.statusCode());
			assertNotNull(response_str);
		}


	// DELETE /categories/:id/projects/:id
	/*Notes: Could not find any instances with /categories/1/projects/0
	 */
	@Test
	public void test_delete_todo_category_with_id_project() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1/projects/0")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		assertEquals(404,response.statusCode());
	}

	// POST /categories/:id/todos   
	@Test
	public void test_post_categories_with_id_and_todos() throws IOException, InterruptedException{
		var values = new HashMap<String, String>() {{
			put("id","2");
			put("title","Office");
		}};
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(values);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1/todos"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(404,response.statusCode());

	}

	//////////////////////////////////16. /categories/:id/todos/:id//////////////////////////////////

	// DELETE /categories/:id/todos/:id
	/*Notes: Cannot find any instances with categories/1/todos/0
	    from exploratory testing, cannot find such an instance, thus the expected code is 404
	 */
	@Test
	public void test_delete_todo_with_ids_category_with_id_project() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/1/todos/0")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		assertEquals(404,response.statusCode());
	}
	
	   @After
	   public void category_delete() throws IOException, InterruptedException {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:4567/categories/2")).DELETE()
					.build();
			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			assertEquals(404,response.statusCode());
		}
}
