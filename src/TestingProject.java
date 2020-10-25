import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestingProject {
	
	@Test
	public void test_get_projects_with_id_categories() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).GET() // GET
																														// is
																														// default
				.build();

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

		//System.out.println(response.statusCode());
		assertEquals(200,response.statusCode());
	}
	

	@Test
	public void test_post_projects_with_id_categories() throws IOException, InterruptedException{
		var values = new HashMap<String, String>() {{
            
			put("description","HELLO");
			put("id","2");
			put("title","Office");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/1/categories"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        System.out.println(response.statusCode());
        assertEquals(404,response.statusCode());
    
	}
	@Test
	public void test_get_categories() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).GET() // GET
																														// is
																														// default
				.build();

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

		//System.out.println(response.statusCode());
		assertEquals(200,response.statusCode());
	}
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
	@Test
	public void test_get_categories_with_id() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).GET().build();

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

		//System.out.println(response.statusCode());
		assertEquals(200,response.statusCode());
	}
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
	
	@Test
	public void test_get_categories_with_id_and_todos() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1/todos")).GET() // GET
																														// is
																														// default
				.build();

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

		//System.out.println(response.statusCode());
		assertEquals(200,response.statusCode());
	}
	
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
        assertEquals(201,response.statusCode());
    
	}
	@Test
	public void test_dummy() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/4")).GET() // GET
																														// is
																														// default
				.build();

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

		//System.out.println(response.statusCode());
		assertEquals(404,response.statusCode());
	}
	
	
}