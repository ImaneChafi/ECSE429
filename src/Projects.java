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
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for projects: " + T1);
		System.out.print("T2 for projects: " + T2);

	}

	// GET /projects
	/* Notes: return a list of projects
	 */
	@Test
	public void test_project_get() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects")).GET()
				.build();
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		assertNotNull(response_str);
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_project_get: " + T1);
		System.out.print("T2 for test_project_get: " + T2);
	}

	// HEAD /projects
	/*Notes: HEAD request test, a HEAD request is a GET request without a message body
	 */
	@Test
	public void test_projects_head() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_head: " + T1);
		System.out.print("T2 for test_projects_head: " + T2);

	}

	// POST /projects
	/*Notes: post fails with error message --> Failed Validation: Not allowed to create with id
	 */
	@Test
	public void test_projects_post_fail() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("id", "9");
			put("title", "test1");
			put("completed", "false");
			put("active", "true");
			put("description", "hello world");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(400,response.statusCode());
		String error = "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
		assertEquals(error, response_str.body());
		

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_post_fail: " + T1);
		System.out.print("T2 for test_projects_post_fail: " + T2);
	}

	// POST /projects
	/*Notes: post fails with error message --> Failed Validation: active should be BOOLEAN, completed should be BOOLEAN
    to resolve this problem, simply remove the "" surrounding the boolean variables
	 */
	@Test
	public void test_projects_post_fail_bool() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("title", "test1");
			put("completed", "false");
			put("active", "true");
			put("description", "hello world");
		}};
		long start_t2 = System.nanoTime();
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
		long T2_end = System.nanoTime();
		assertEquals(400,response.statusCode());
		String error = "{\"errorMessages\":[\"Failed Validation: active should be BOOLEAN, completed should be BOOLEAN\"]}";
		assertEquals(error, response_str.body());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_post_fail_bool: " + T1);
		System.out.print("T2 for test_projects_post_fail_bool: " + T2);

	}

	// POST /projects
	/*Notes: success */
	@Test
	public void test_projects_post() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, Object>() {{
			put("title", "test1");
			put("completed", false);
			put("active", true);
			put("description", "hello world");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		assertEquals(201,response.statusCode());
		assertNotNull(response);
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_post: " + T1);
		System.out.print("T2 for test_projects_post: " + T2);

	}
	//////////////////////////////////8. /projects/:id///////////////////////////////////

	// GET /projects/:id
	/*Notes: Error message with could not find an instance with projects/1
	 */
	@Test
	public void test_project_get_id() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1")).GET()
				.build();
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(404,response.statusCode());
		assertNotNull(response_str.body());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_project_get_id: " + T1);
		System.out.print("T2 for test_project_get_id: " + T2);

	}

	// GET /projects/:id
	/* Notes: As in exploratory testing, this is supposed to pass with a 404 error.
	 */
	@Test
	public void test_project_get_id_f() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1000")).GET()
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		String error = "{\"errorMessages\":[\"Could not find an instance with projects/1000\"]}";
		assertEquals(404,response.statusCode());
		assertEquals(error, response_str.body());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_project_get_id_f: " + T1);
		System.out.print("T2 for test_project_get_id_f: " + T2);
	}

	// HEAD /projects/:id
	/* Notes: This test is to check if from the exploratory testing, it is true that the HEAD api works */
	@Test
	public void test_projects_head_id() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,
				HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		HttpHeaders headers = response.headers();
		assertEquals(404,response.statusCode());
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_head_id: " + T1);
		System.out.print("T2 for test_projects_head_id: " + T2);
	}

	// POST /projects/:id
	/*Notes: This test is to see if the post on id not existing really works, as seen in exploratory testing*/
	@Test
	public void test_projects_post_id() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
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
		long T2_end = System.nanoTime();
		assertEquals(404,response.statusCode());
		assertNotNull(response_str);
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_post_id: " + T1);
		System.out.print("T2 for test_projects_post_id: " + T2);

	}

	// PUT /projects/:id
	/* Notes: This test is to see if the PUT api works without id, as seen in exploratory testing*/
	@Test
	public void test_projects_put_id() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var body = new HashMap<String, Object>(){{
			put("title", "test1");
			put("completed", false);
			put("active", true);
			put("description", "hello world2");
		}};
		long start_t2 = System.nanoTime();
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
		long T2_end = System.nanoTime();
		assertEquals(404,response.statusCode());
		assertNotNull(response_str);
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_put_id: " + T1);
		System.out.print("T2 for test_projects_put_id: " + T2);

	}

	// DELETE /projects/:id
	/*Notes: This test is to see if the delete project, as seen in exploratory testing, works*/
	@Test
	public void test_project_delete() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1")).DELETE()
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
		System.out.print("T1 for test_project_delete: " + T1);
		System.out.print("T2 for test_project_delete: " + T2);

	}

	//////////////////////////////////8. /projects/:id/categories///////////////////////////////////

	// GET /projects/:id/categories
	@Test
	public void test_project_get_id_cat() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1/categories")).GET()
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
		long T2_end = System.nanoTime();
		assertNotNull(response_str);
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_project_get_id_cat: " + T1);
		System.out.print("T2 for test_project_get_id_cat: " + T2);

	}

	// HEAD /projects/:id/categories
	@Test
	public void test_projects_head_id_cat() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1/categories"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_head_id_cat: " + T1);
		System.out.print("T2 for test_projects_head_id_cat: " + T2);

	}

	// GET /projects/:id/tasks
	@Test
	public void test_get_projects_with_id_categories() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).GET() // GET																											// default
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_get_projects_with_id_categories: " + T1);
		System.out.print("T2 for test_get_projects_with_id_categories: " + T2);

	}
	// POST /projects/:id/categories 
	/* Notes: create an instance of a relationship between categories and projects
	 */
	@Test
	public void test_projects_post_id_cat() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
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
		long start_t2 = System.nanoTime();
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
		long T2_end = System.nanoTime();
		assertEquals(201,response.statusCode());
		assertNotNull(response);
		assertNotNull(response1);
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_post_id_cat: " + T1);
		System.out.print("T2 for test_projects_post_id_cat: " + T2);

	}

	// POST /projects/:id/categories  
	/* Notes:project id not found, expect 404 
	 */
	@Test
	public void test_projects_post_id_cat_f() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
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
		long start_t2 = System.nanoTime();
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
		long T2_end = System.nanoTime();
		assertEquals(404,response1.statusCode());
		assertEquals(error, response1.body());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_post_id_cat_f: " + T1);
		System.out.print("T2 for test_projects_post_id_cat_f: " + T2);

	}

	//////////////////////////////////9. /projects/:id/categories/:id///////////////////////////////////

	// DELETE /projects/:id/categories  
	/* Notes:delete project with inexistant category, expect 404 
	 */
	@Test
	public void test_projects_with_id_categories_its_id_delete() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1/categories/4")).DELETE()
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
		System.out.print("T1 for test_projects_with_id_categories_its_id_delete: " + T1);
		System.out.print("T2 for test_projects_with_id_categories_its_id_delete: " + T2);

	}

	//////////////////////////////////10. /projects/:id/tasks//////////////////////////////////



	// HEAD /projects
	/*Notes: HEAD request test, a HEAD request is a GET request without a message body
	 */
	@Test
	public void test_projects_head_tasks() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1/tasks"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_head_tasks: " + T1);
		System.out.print("T2 for test_projects_head_tasks: " + T2);

	}

	// POST /projects
	/*Notes: post doesnt work with error message, expect 404 --> Failed Validation: Not allowed to create with id
	 */
	@Test
	public void test_projects_post_tasks() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		var value = new HashMap<String, String>() {{
			put("id", "9");
			put("title", "test1");
			put("completed", "false");
			put("active", "true");
			put("description", "hello world");
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(value);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(400,response.statusCode());
		String error = "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
		assertEquals(error, response_str.body());
		
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_projects_post_tasks: " + T1);
		System.out.print("T2 for test_projects_post_tasks: " + T2);

	}

	//////////////////////////////////11. /projects/:id/tasks/:id//////////////////////////////////

	// DELETE /projects/:id/tasks/:id
	@Test
	public void test_projects_with_id_and_its_tasks_with_id_delete() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/1/tasks/1")).DELETE()
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
		System.out.print("T1 for test_projects_with_id_and_its_tasks_with_id_delete: " + T1);
		System.out.print("T2 for test_projects_with_id_and_its_tasks_with_id_delete: " + T2);

	}

	//////////////////////////////////12. /categories//////////////////////////////////

	/*
    Notes: Determine the status of a web page when accessing categories
	 */
	@Test
	public void categories_status() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories"))
				.GET().build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for categories_status: " + T1);
		System.out.print("T2 for categories_status: " + T2);

	}

	//GET /categories
	@Test
	public void test_get_categories() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories")).GET() // GET																											// default
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_get_categories: " + T1);
		System.out.print("T2 for test_get_categories: " + T2);

	}

	// HEAD /categories
	@Test
	public void test_cat_head() throws IOException, InterruptedException {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/categories"))
				.method("HEAD", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		HttpHeaders headers = response.headers();
		long T2_end = System.nanoTime();
		assertEquals(200,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_cat_head: " + T1);
		System.out.print("T2 for test_cat_head: " + T2);

	}

	// POST /categories
	/*Except 400 error as id was provided, as seen in exploratory testing*/
	@Test
	public void test_post_categories() throws IOException, InterruptedException{
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
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
		long T2_end = System.nanoTime();
		System.out.println(response.body());
		System.out.println(response.statusCode());
		assertEquals(400,response.statusCode());
		
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_post_categories: " + T1);
		System.out.print("T2 for test_post_categories: " + T2);

	}

	 @After
	   public void project_id_delete() throws IOException, InterruptedException {
		    long start_t1 = System.nanoTime();
		    long start_t2 = System.nanoTime();
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:4567/projects/970420/categories/2")).DELETE()
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
			System.out.print("T1 for project_id_delete: " + T1);
			System.out.print("T2 for project_id_delete: " + T2);

		}
}
