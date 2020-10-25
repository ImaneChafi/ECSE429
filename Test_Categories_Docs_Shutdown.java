import com.fasterxml.jackson.databind.ObjectMapper;
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

public class Test_Categories {
    @Test
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
    Determine the status of a web page when accessing categories
     */
    @Test
    public void todos() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/categories"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
    }

    /*
    DELETE /categories/:id/projects/:id
    Could not find any instances with /categories/1/projects/0
     */
    @Test
    public void test_delete_todo_category_with_id_project() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/categories/1/projects/0")).DELETE()
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        assertEquals(404,response.statusCode());
    }

    /*
    Determine the status of a web page when accessing categories with ids and categories
     */
    @Test
    public void projects() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/categories"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
    }

    /*
    GET /categories/:id/todos
    return all the categories items related to category and todos, with given id, by the relationship named todos
     */
    @Test
    public void test_category_id_todos_get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/categories/1/todos")).GET()
                .build();
        // return a list of objects with /projects
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response_str.body());
        HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        assertNotNull(response_str);
    }

    /*
    HEAD /categories/:id/todos
	headers for the todo items related to category, with given id, by the relationship named todos*/
    @Test
    public void test_categories_ids_todos_head() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("/categories/1/todos"))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
    }

    /*
	POST /categories/:id/todos   
	create an instance of a relationship named categories between todos instance :id and the category instance represented by the id in the body of the message     */
    @Test
    public void test_categories_todos_post_fail() throws IOException, InterruptedException {

        var value = new HashMap<String, String>() {{
            put("id", "10");
            put("title", "test1");
            put("description", "hello world");
        }};

        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(value);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/categories"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(400,response.statusCode());
        String error = "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
//        System.out.println(response_str.body());
        assertEquals(error, response_str.body());
    }


    /*
    DELETE /categories/:id/todos/:id
    Cannot find any instances with categories/1/todos/0
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
//        System.out.println(response.body());
        assertEquals(404,response.statusCode());
    }
    /*
    GET /docs
    success
     */
    @Test
    public void test_docs() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/docs")).GET()
                .build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response_str.body());
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        assertNotNull(response_str.body());
    }

    /*
    GET /shutdown
    success
     */
    @Test
    public void test_shutdown() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/shutdown")).GET()
                .build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response_str.body());
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(404,response.statusCode()); //The responses shouldn't be working anymore
    }

}
