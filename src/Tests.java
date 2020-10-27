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

public class Tests {
	
//////////////////See if server has started////////////////////////////////////////////////////
    
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
    
/////////////////////////1./todos//////////////////////////////////////////////////////////
    
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
        assertEquals(200,response.statusCode());
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
    
    // POST /todos/:id/categories
    /* Notes: This test checks if the POST API indeed returns a 404 error with ids, as seen in the exploratory testing. */
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
        assertEquals(200,response.statusCode());
    }
 
//////////////////////////////////7. /projects///////////////////////////////////
    
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
        assertEquals(200,response.statusCode());
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
        assertEquals(200,response.statusCode());
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
        assertEquals(200,response.statusCode());
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
        assertEquals(400,response.statusCode());
    }

//////////////////////////////////10. /projects/:id/tasks//////////////////////////////////
   
    // GET /projects/:id/tasks
    @Test
 	public void test_get_projects_with_id_categories() throws IOException, InterruptedException {
 		HttpClient client = HttpClient.newHttpClient();
 		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).GET() // GET																											// default
 				.build();
 		HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
 		assertEquals(200,response.statusCode());
 	}
    
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

//////////////////////////////////13. /categories/:id//////////////////////////////////
    
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
	        assertEquals(200,response.statusCode());
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
         assertEquals(201,response.statusCode());
 	}
 	
//////////////////////////////////14. /categories/:id/projects/:id //////////////////////////////////
 	
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
	      assertEquals(201,response.statusCode());
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
          assertEquals(201,response.statusCode());
      
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
 
//////////////////////////////////15. /docs//////////////////////////////////

    // GET /docs
    /* Notes: success */
    @Test
    public void test_docs() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/docs")).GET()
                .build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        assertNotNull(response_str.body());
    }
    
    // GET /docs
    /* Notes: return 404 error, wrong endpoint (adding a / at the end) 
     */
    @Test
    public void test_docs_failure() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/docs/")).GET()
                .build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        assertEquals(404,response.statusCode());
        assertNotNull(response_str.body());
    }
    
    //SHUTDOWN is in another file and can be run individually to end the server
    
}
