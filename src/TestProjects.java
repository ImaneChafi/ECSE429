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

    //Running webpage
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

    // 1) /todos
    // GET /todos
    /* Notes: */
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
        //assertNotNull(response.body());
    }
    // HEAD /todos
    /* Notes: */
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
    /* Notes: */
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
        assertEquals(400,response.statusCode());
        //TODO: check it's the correct err msg
        String errorMessages = (String)body.getList("errorMessages").get(0)
        Assertions.assertTrue(errorMessages.contains("Failed Validation"), errorMessages)
        // "{\"errorMessages\":[\"Invalid Creation: Failed Validation: Not allowed to create with id\"]}";
        //assertEquals(error, response.body());
    }

    //Tests from Swagger GitHub:
    @Test
    public void cannotCreateWithInvalidTodoBlankTitlePosts(){

        //{"title": "a specific to do Title"}
        final HashMap<String, String> givenBody = new HashMap<String, String>();
        givenBody.put("title", "");

        final JsonPath body = given().body(givenBody).
                when().post("/todos").
                then().
                statusCode(400).
                contentType(ContentType.JSON).
                and().extract().body().jsonPath();

        String errorMessages = (String)body.getList("errorMessages").get(0);
        Assertions.assertTrue(errorMessages.contains("title : can not be empty"),errorMessages);
    }

    @Test
    public void cannotCreateWithInvalidTodoBlankBodyPosts(){

        final JsonPath body = given().body("{}").
                when().post("/todos").
                then().
                statusCode(400).
                contentType(ContentType.JSON).
                and().extract().body().jsonPath();

        Assertions.assertEquals("title : field is mandatory",
                body.getList("errorMessages").get(0));
    }

    @Test
    public void cannotCreateWithInvalidTodoNoBodyPosts(){

        final JsonPath body = given().body("").
                when().post("/todos").
                then().
                statusCode(400).
                contentType(ContentType.JSON).
                and().extract().body().jsonPath();

        Assertions.assertEquals("title : field is mandatory",
                body.getList("errorMessages").get(0));
    }

    @Test
    public void canCreateWithPost(){

        final HashMap<String, String> givenBody = new HashMap<String, String>();
        givenBody.put("title", "a specific todo Title");

        final Response response = given().body(givenBody).
                when().post("/todos").
                then().
                statusCode(201).
                contentType(ContentType.JSON).and().extract().response();

        Assertions.assertEquals(
                        response.header("Location"),
                "todos/" + response.header("X-Thing-Instance-GUID"));

        final JsonPath body = response.jsonPath();

        Assertions.assertEquals("false", body.get("doneStatus"));
        Assertions.assertEquals(response.header("X-Thing-Instance-GUID"), body.get("guid"));
        Assertions.assertEquals("", body.get("description"));
        Assertions.assertEquals("a specific todo Title", body.get("title"));
    }

    // 2) /todos/:id
    // GET /todos/:id
    /* Notes: */
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
        //assertNotNull(response.body());
    }
    // HEAD /todos/:id
    /* Notes: */
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

    // Test from Swagger GitHub:
    // POST /todos/:id
    // PUT /todos/:id
    // DELETE /todos/:id
    /* Notes: */
    @Test
    public void canCreateAndAmendSequence(){

        String specificGuid = "3e788069-1d22-4aa1-a03b-5689eab2f321";

        // CREATE WITH PUT

        final HashMap<String, String> givenBody = new HashMap<String, String>();
        givenBody.put("title", "a specific todo Title for put");

        JsonPath body = given().body(givenBody).
                when().put("/todos/" + specificGuid).
                then().
                statusCode(201).
                contentType(ContentType.JSON).
                header("Location", "todos/" + specificGuid).
                header("X-Thing-Instance-GUID", specificGuid).
                and().extract().body().jsonPath();

        Assertions.assertEquals("false", body.get("doneStatus"));
        Assertions.assertEquals(specificGuid, body.get("guid"));
        Assertions.assertEquals("", body.get("description"));
        Assertions.assertEquals("a specific todo Title for put", body.get("title"));


        // AMEND with PUT
        givenBody.put("title", "a put amended specific todo Title for put");

        Response response = given().body(givenBody).
                when().put("/todos/" + specificGuid).
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                and().extract().response();


        Assertions.assertFalse(response.headers().hasHeaderWithName("Location"));
        Assertions.assertFalse(response.headers().hasHeaderWithName("X-Thing-Instance-GUID"));

        body = response.getBody().jsonPath();

        Assertions.assertEquals("false", body.get("doneStatus"));
        Assertions.assertEquals(specificGuid, body.get("guid"));
        Assertions.assertEquals("", body.get("description"));
        Assertions.assertEquals("a put amended specific todo Title for put", body.get("title"));


        // AMEND with POST
        givenBody.put("title", "a specific todo Title Amended");

        response = given().body(givenBody).
                when().post("/todos/" + specificGuid).
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                and().extract().response();


        Assertions.assertFalse(response.headers().hasHeaderWithName("Location"));
        Assertions.assertFalse(response.headers().hasHeaderWithName("X-Thing-Instance-GUID"));

        body = response.getBody().jsonPath();

        Assertions.assertEquals("false", body.get("doneStatus"));
        Assertions.assertEquals(specificGuid, body.get("guid"));
        Assertions.assertEquals("", body.get("description"));
        Assertions.assertEquals("a specific todo Title Amended", body.get("title"));

        // GET the TO DO item
        response = when().get("/todos/" + specificGuid).
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                and().extract().response();

        Assertions.assertFalse(response.headers().hasHeaderWithName("Location"));
        Assertions.assertFalse(response.headers().hasHeaderWithName("X-Thing-Instance-GUID"));

        body = response.getBody().jsonPath();

        Assertions.assertEquals("false", body.get("todos[0].doneStatus"));
        Assertions.assertEquals(specificGuid, body.get("todos[0].guid"));
        Assertions.assertEquals("", body.get("todos[0].description"));
        Assertions.assertEquals("a specific todo Title Amended", body.get("todos[0].title"));

        // DELETE the TO DO item
        response = when().delete("/todos/" + specificGuid).
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                and().extract().response();

        // Cannot GET a deleted to do item
        response = when().get("/todos/" + specificGuid).
                then().
                statusCode(404).
                contentType(ContentType.JSON).
                and().extract().response();

        Assertions.assertEquals("Could not find an instance with todos/3e788069-1d22-4aa1-a03b-5689eab2f321",
                response.getBody().jsonPath().get("errorMessages[0]"));
    }

    // 3) /todos/:id/tasksof
    // GET /todos/:id/tasksof
    /* Notes: */
    @Test
    public void test_todos_taskof_get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos/1/taskof")).GET()
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        System.out.println(response.body());
        assertNotNull(response.body());
    }
    // HEAD /todos/:id/tasksof
    /* Notes: */
    @Test
    public void test_todos_taskof_head() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:4567/todos/1/taskof"))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        HttpHeaders headers = response.headers();
        assertEquals(200,response.statusCode());
    }
    // POST /todos/:id/tasksof

    // 4) /todos/:id/tasksof/:id
    // DELETE /todos/:id/tasksof/:id

    // 5) /todos/:id/categories
    // GET /todos/:id/categories
    /* Notes: */
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
        assertNotNull(response.body());
    }
    // HEAD /todos/:id/categories
    /* Notes: */
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

}//End of TestProjects
