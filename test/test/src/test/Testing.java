package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Testing{
	
	@Test
	public void test_projects_with_id_categories() throws IOException, InterruptedException {
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
	public void test() throws IOException, InterruptedException{
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
        assertEquals(200,response.statusCode());
    
	}
	@Test
	public void test_categories() throws IOException, InterruptedException {
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
	public void test_categories_with_id() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).GET().build();

		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

		//System.out.println(response.statusCode());
		assertEquals(200,response.statusCode());
	}
	@Test
	public void test_categories_with_id_and_todos() throws IOException, InterruptedException {
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

