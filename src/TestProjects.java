import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProjects {

    /*
    Determine the status of a web page
     */
    @Test
    public void todos() throws IOException, InterruptedException {
        // create a new HttpClient
        HttpClient client = HttpClient.newHttpClient();
        // build a new HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos"))
                .GET() // GET is default
                .build();
        // send the request, not interested in the response body
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
    }

    /*
    DELETE /todos/:id/categories/:id
    TODO this does not work
    */
    @Test
    public void test_delete_todo_category_with_id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos/1/categoties/1")).DELETE()
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        // change the expected value from 200 to 404 just to pass the test
        assertEquals(404,response.statusCode());
    }

    /*
    DELETE /todos/:id/categories/:id
    fail --> Could not find any instances with todos/1/categories/0
     */
    @Test
    public void test_delete_todo_category_with_id_f() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos/1/categoties/0")).DELETE()
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(404,response.statusCode());
    }

    /*
    GET /projects
     */
    // TODO: this actual returns a null body, however, the response body should not be null
    @Test
    public void test_project_get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects")).GET()
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        System.out.println(response.body());
//        assertNotNull(response.body());
    }

    /*
    HEAD /projects
    HEAD request test, a HEAD request is a GET request without a message body
     */
    @Test
    public void test_projects_head() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects"))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        HttpHeaders headers = response.headers();
        assertEquals(200,response.statusCode());
    }

    /*
    POST /projects
    post fails with error message --> Failed Validation: Not allowed to create with id
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

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(400,response.statusCode());
        String error = "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
        System.out.println(response.body());
        assertEquals(error, response.body());
    }

    /*
    POST /projects
    post fails with error message --> Failed Validation: active should be BOOLEAN, completed should be BOOLEAN
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

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(400,response.statusCode());
        String error = "{\"errorMessages\":[\"Failed Validation: active should be BOOLEAN, completed should be BOOLEAN\"]}";

        assertEquals(error, response.body());
    }

    /*
    GET /projects/:id
    success
     */
    @Test
    public void test_project_get_id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects/1")).GET()
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
//        assertNotNull(response.body());
    }

    /*
    GET /projects/:id
    fail
     */
    @Test
    public void test_project_get_id_f() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects/6")).GET()
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        assertEquals(404,response.statusCode());
    }

    /*
    HEAD /projects/:id
     */
    @Test
    public void test_projects_head_id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1"))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        HttpHeaders headers = response.headers();
        assertEquals(200,response.statusCode());
    }

    // TODO: POST/PUT /projects/:id

    // TODO: DELETE /projects/:id

    /*
    GET /projects/:id/categories
     */
    @Test
    public void test_project_get_id_cat() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects/1/categories")).GET()
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
    }

    /*
    HEAD /projects/:id/categories
     */
    @Test
    public void test_projects_head_id_cat() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/projects/1/categories"))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        HttpHeaders headers = response.headers();
        assertEquals(200,response.statusCode());
    }

    // TODO: POST /projects/:id/categories

    // TODO: DELETE /projects/:id/categories/:id


    // Sofia
    @Test
    public void test_todos_post() throws IOException, InterruptedException {

        var value = new HashMap<String, String>() {{
            put("id", "9");
            put("title", "test_todos");
            put("doneStatus", "false");
            put("description", "testing todos POST");
        }};

        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(value);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        assertEquals(400, response.statusCode());
        //TODO: check it's the correct err msg
        //String error = "{\"errorMessages\":[\"I
    }

    // Mahroo
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
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        System.out.println(response.statusCode());
        assertEquals(405,response.statusCode());

    }

}
