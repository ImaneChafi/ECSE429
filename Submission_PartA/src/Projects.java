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

public class Projects {

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
	/*
    Determine the status of a web page when accessing projects
	 */
	@Test
	public void projects() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}

	// GET /projects
	/* Notes: return a list of projects
	 */
	@Test
	public void test_project_get() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects")).GET()
				.build();
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
		assertNotNull(response_str);
	}

	// HEAD /projects
	/*Notes: HEAD request test, a HEAD request is a GET request without a message body
	 */
	@Test
	public void test_projects_head() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}

	// POST /projects
	/*Notes: post fails with error message --> Failed Validation: Not allowed to create with id
	 */
	@Test
	public void test_projects_post_fail() throws IOException, InterruptedException {

		var value = new HashMap<String, String>() {{
			put("id", "9");
			put("title", "test1");
			put("completed", "false");
			put("active", "true");
			put("description", "hello world");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(400,response.statusCode());
		String error = "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
		assertEquals(error, response_str.body());
	}

	// POST /projects
	/*Notes: post fails with error message --> Failed Validation: active should be BOOLEAN, completed should be BOOLEAN
    to resolve this problem, simply remove the "" surrounding the boolean variables
	 */
	@Test
	public void test_projects_post_fail_bool() throws IOException, InterruptedException {

		var value = new HashMap<String, String>() {{
			put("title", "test1");
			put("completed", "false");
			put("active", "true");
			put("description", "hello world");
		}};

		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		//        System.out.println(response_str.body());
		assertEquals(400,response.statusCode());
		String error = "{\"errorMessages\":[\"Failed Validation: active should be BOOLEAN, completed should be BOOLEAN\"]}";
		assertEquals(error, response_str.body());
	}

	// POST /projects
	/*Notes: success */
	@Test
	public void test_projects_post() throws IOException, InterruptedException {

		var value = new HashMap<String, Object>() {{
			put("title", "test1");
			put("completed", false);
			put("active", true);
			put("description", "hello world");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		assertEquals(201,response.statusCode());
		assertNotNull(response);
	}
	//////////////////////////////////8. /projects/:id///////////////////////////////////

	// GET /projects/:id
	/*Notes: Error message with could not find an instance with projects/1
	 */
	@Test
	public void test_project_get_id() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1")).GET()
				.build();
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
		assertNotNull(response_str.body());
	}

	// GET /projects/:id
	/* Notes: As in exploratory testing, this is supposed to pass with a 404 error.
	 */
	@Test
	public void test_project_get_id_f() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1000")).GET()
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		String error = "{\"errorMessages\":[\"Could not find an instance with projects/1000\"]}";
		assertEquals(404,response.statusCode());
		assertEquals(error, response_str.body());
	}

	// HEAD /projects/:id
	/* Notes: This test is to check if from the exploratory testing, it is true that the HEAD api works */
	@Test
	public void test_projects_head_id() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(404,response.statusCode());
	}

	// POST /projects/:id
	/*Notes: This test is to see if the post on id not existing really works, as seen in exploratory testing*/
	@Test
	public void test_projects_post_id() throws IOException, InterruptedException {

		var body = new HashMap<String, Object>(){{
			put("title", "test1");
			put("completed", false);
			put("active", true);
			put("description", "hello world");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(body);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
		assertNotNull(response_str);
	}

	// PUT /projects/:id
	/* Notes: This test is to see if the PUT api works without id, as seen in exploratory testing*/
	@Test
	public void test_projects_put_id() throws IOException, InterruptedException {

		var body = new HashMap<String, Object>(){{
			put("title", "test1");
			put("completed", false);
			put("active", true);
			put("description", "hello world2");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(body);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1"))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		assertEquals(404,response.statusCode());
		assertNotNull(response_str);
	}

	// DELETE /projects/:id
	/*Notes: This test is to see if the delete project, as seen in exploratory testing, works*/
	@Test
	public void test_project_delete() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(404,response.statusCode());
	}

	//////////////////////////////////8. /projects/:id/categories///////////////////////////////////

	// GET /projects/:id/categories
	@Test
	public void test_project_get_id_cat() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1/categories")).GET()
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		assertNotNull(response_str);
		assertEquals(200,response.statusCode());
	}

	// HEAD /projects/:id/categories
	@Test
	public void test_projects_head_id_cat() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1/categories"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(200,response.statusCode());
	}

	// GET /projects/:id/tasks
	@Test
	public void test_get_projects_with_id_categories() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).GET() // GET																											// default
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}
	// POST /projects/:id/categories 
	/* Notes: create an instance of a relationship between categories and projects
	 */
	@Test
	public void test_projects_post_id_cat() throws IOException, InterruptedException {
		var project = new HashMap<String, Object>() {{
			put("title", "test1");
			put("completed", false);
			put("active", true);
			put("description", "hello world");
		}};
		var categorie = new HashMap<String, String>() {{
			put("title", "link");
			put("description", "realtion");
		}};
		ObjectMapper om = new ObjectMapper();
		ObjectMapper om1 = new ObjectMapper();
		String requestProp = om.writeValueAsString(project);
		String requestCat = om1.writeValueAsString(categorie);
		HttpClient client = HttpClient.newHttpClient();
		HttpClient client1 = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestProp)).build();
		HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/2/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestCat)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
		assertEquals(201,response.statusCode());
		assertNotNull(response);
		assertNotNull(response1);
	}

	// POST /projects/:id/categories  
	/* Notes:project id not found, expect 404 
	 */
	@Test
	public void test_projects_post_id_cat_f() throws IOException, InterruptedException {
		var project = new HashMap<String, Object>() {{
			put("title", "test1");
			put("completed", false);
			put("active", true);
			put("description", "hello world");
		}};
		var categorie = new HashMap<String, String>() {{
			put("title", "link");
			put("description", "realtion");
		}};
		ObjectMapper om = new ObjectMapper();
		ObjectMapper om1 = new ObjectMapper();
		String requestProp = om.writeValueAsString(project);
		String requestCat = om1.writeValueAsString(categorie);
		HttpClient client = HttpClient.newHttpClient();
		HttpClient client1 = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestProp)).build();
		HttpRequest request1 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/970420/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestCat)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
		String error = "{\"errorMessages\":[\"Could not find parent thing for relationship projects/970420/categories\"]}";
		assertEquals(404,response1.statusCode());
		assertEquals(error, response1.body());
	}

	//////////////////////////////////9. /projects/:id/categories/:id///////////////////////////////////

	// DELETE /projects/:id/categories  
	/* Notes:delete project with inexistant category, expect 404 
	 */
	@Test
	public void test_projects_with_id_categories_its_id_delete() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1/categories/4")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(404,response.statusCode());
	}

	//////////////////////////////////10. /projects/:id/tasks//////////////////////////////////



	// HEAD /projects
	/*Notes: HEAD request test, a HEAD request is a GET request without a message body
	 */
	@Test
	public void test_projects_head_tasks() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1/tasks"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}

	// POST /projects
	/*Notes: post doesnt work with error message, expect 404 --> Failed Validation: Not allowed to create with id
	 */
	@Test
	public void test_projects_post_tasks() throws IOException, InterruptedException {

		var value = new HashMap<String, String>() {{
			put("id", "9");
			put("title", "test1");
			put("completed", "false");
			put("active", "true");
			put("description", "hello world");
		}};
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(400,response.statusCode());
		String error = "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
		assertEquals(error, response_str.body());
	}

	//////////////////////////////////11. /projects/:id/tasks/:id//////////////////////////////////

	// DELETE /projects/:id/tasks/:id
	@Test
	public void test_projects_with_id_and_its_tasks_with_id_delete() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1/tasks/1")).DELETE()
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		assertEquals(404,response.statusCode());
	}

	//////////////////////////////////12. /categories//////////////////////////////////

	/*
    Notes: Determine the status of a web page when accessing categories
	 */
	@Test
	public void categories_status() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories"))
				.GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}

	//GET /categories
	@Test
	public void test_get_categories() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories")).GET() // GET																											// default
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		assertEquals(200,response.statusCode());
	}

	// HEAD /categories
	@Test
	public void test_cat_head() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/categories"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		assertEquals(200,response.statusCode());
	}

	// POST /categories
	/*Except 400 error as id was provided, as seen in exploratory testing*/
	@Test
	public void test_post_categories() throws IOException, InterruptedException{
		var values = new HashMap<String, String>() {{

			put("description","HELLO");
			put("id","2");
			put("title","Office");
		}};
		var objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(values);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(400,response.statusCode());
	}

	 @After
	   public void project_id_delete() throws IOException, InterruptedException {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:4567/projects/970420/categories/2")).DELETE()
					.build();
			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
			assertEquals(404,response.statusCode());
		}
}
