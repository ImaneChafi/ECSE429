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

public class TestProjects {

    /*
    Determine the status of main web page
    */
    @Test
    public void main_page() throws IOException, InterruptedException {
        try {
            // create a new HttpClient
            HttpClient client = HttpClient.newHttpClient();
            // build a new HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:4567"))
                    .GET().build(); // GET is default
            // send the request, not interested in the response body
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            assertEquals(302, response.statusCode());
        } catch (IOException e) {
            final JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "make sure you have start running the server.");
            exit(-1);
        }
    }

    /*
    Determine the status of a web page when accessing todos
     */
    @Test
    public void todos() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos"))
                .GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
    }

    /*
    DELETE /todos/:id/categories/:id
    fail --> Could not find any instances with todos/1/categories/0
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
//        System.out.println(response.body());
        assertEquals(404,response.statusCode());
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

    /*
    GET /projects
    return a list of projects
     */
    @Test
    public void test_project_get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects")).GET()
                .build();
        // return a list of objects with /projects
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response_str.body());
        HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        assertNotNull(response_str);
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
        HttpResponse<Void> response = client.send(request,HttpResponse.BodyHandlers.discarding());
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

        HttpResponse<String> response_str = client.send(request,HttpResponse.BodyHandlers.ofString());
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(400,response.statusCode());
        String error = "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
//        System.out.println(response_str.body());
        assertEquals(error, response_str.body());
    }

    /*
    POST /projects
    post fails with error message --> Failed Validation: active should be BOOLEAN, completed should be BOOLEAN
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

    /*
    POST /projects
    success
     */
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
//        System.out.println(response.body());
        assertEquals(201,response.statusCode());
        assertNotNull(response);
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
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response_str.body());
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        assertNotNull(response_str.body());
    }

    /*
    GET /projects/:id
    fail
     */
    @Test
    public void test_project_get_id_f() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects/1000")).GET()
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response_str.body());
        String error = "{\"errorMessages\":[\"Could not find an instance with projects/1000\"]}";
        assertEquals(404,response.statusCode());
        assertEquals(error, response_str.body());
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

    /*
    POST /projects/:id
     */
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
//        System.out.println(response_str.body());
//        System.out.println(response.statusCode());
        assertEquals(200,response.statusCode());
        assertNotNull(response_str);
    }

    /*
    PUT /projects/:id
     */
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
//        System.out.println(response_str.body());
//        System.out.println(response.statusCode());
        assertEquals(200,response.statusCode());
        assertNotNull(response_str);
    }

    @Test
    public void test_del_project_with_id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/project/1")).DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        assertEquals(404,response.statusCode());
    }

    /*
    GET /projects/:id/categories
     */
    @Test
    public void test_project_get_id_cat() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects/1/categories")).GET()
                .build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response_str.body());
        assertNotNull(response_str);
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
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        HttpHeaders headers = response.headers();
        assertEquals(200,response.statusCode());
    }

    /*
    POST /projects/:id/categories  --> success
    create an instance of a relationship between categories and projects
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

//        System.out.println(response.body());
//        System.out.println(response1.body());
        assertEquals(201,response.statusCode());
        assertNotNull(response);
        assertNotNull(response1);
    }

    /*
    POST /projects/:id/categories  --> fail
    project id not found
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

//        System.out.println(response.body());
//        System.out.println(response1.body());
        String error = "{\"errorMessages\":[\"Could not find parent thing for relationship projects/970420/categories\"]}";
        assertEquals(404,response1.statusCode());
        assertEquals(error, response1.body());
    }

    @Test
    public void test_del_project_cat_id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/project/1/categories/1"))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        assertEquals(404,response.statusCode());
    }

}
