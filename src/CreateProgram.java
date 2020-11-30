import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Random;

public class CreateProgram {

    /**
     * helper method to create todos
     * @param title title of the todos created
     * @param doneStatus status of the todos created
     * @param description a brief description of the todos created
     * @throws Exception
     */
    public static void create_todos(String title, boolean doneStatus, String description) throws Exception {
        var todo = new HashMap<String, Object>() {{
           put("title", title);
           put("doneStatus", doneStatus);
           put("description", description);
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to delete a todos with id
     * @param id the id of the todos you want to delete
     * @throws Exception
     */
    public static void delete_todos(int id) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/todos/"+id))
                .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to update/change todos
     * @param id the id of the todos you want to change
     * @param title the title you would like to change with
     * @param doneStatus the doneStatus you would like to change with
     * @param description the description you would like to change with
     * @throws Exception
     */
    public static void change_todos(int id, String title, boolean doneStatus, String description) throws Exception{
        var todo = new HashMap<String, Object>() {{
            put("title", title);
            put("doneStatus", doneStatus);
            put("description", description);
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to create projects
     * @param title title of the project you want to create
     * @param completed completion status of the project
     * @param active active status of the project
     * @param description a brief description of the project
     * @throws Exception
     */
    public static void create_projects(String title, boolean completed, boolean active, String description) throws Exception {
        var todo = new HashMap<String, Object>() {{
            put("title", title);
            put("completed", completed);
            put("active", active);
            put("description", description);
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to delete projects with id
     * @param id the id of the projects you want to delete
     * @throws Exception
     */
    public static void delete_projects(int id) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/projects/"+id))
                .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to update/change projects
     * @param id the id of the projects you want to change
     * @param title the title you would like to change with
     * @param completed the completed value you would like to change with
     * @param active the active value you would like to change with
     * @param description the description you would like to change with
     * @throws Exception
     */
    public static void change_projects(int id, String title, boolean completed, boolean active, String description) throws Exception{
        var todo = new HashMap<String, Object>() {{
            put("title", title);
            put("completed", completed);
            put("active", active);
            put("description", description);
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/"+id))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to create categories
     * @param title title of the categories you want to create
     * @param description a brief description of the categories
     * @throws Exception
     */
    public static void create_categories(String title, String description) throws Exception {
        var todo = new HashMap<String, Object>() {{
            put("title", title);
            put("description", description);
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to delete categories with id
     * @param id the id of the categories you want to delete
     * @throws Exception
     */
    public static void delete_categories(int id) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/categories/"+id))
                .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * helper method to update/change categories
     * @param id the id of the categories you want to change
     * @param title the title you would like to change with
     * @param description the description you would like to change with
     * @throws Exception
     */
    public static void change_categories(int id, String title, String description) throws Exception {
        var todo = new HashMap<String, Object>() {{
            put("title", title);
            put("description", description);
        }};
        var om = new ObjectMapper();
        String requestBody = om.writeValueAsString(todo);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
    }

    /**
     * populate the database for todos, projects, and categories using the methods defined above
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        int num = 50;       // number of instances to generate, you can change the number to test the performance
        // create todos using random generator
        for (int i = 0; i <= num; i++){
            String title = RandomStringUtils.randomAlphabetic(5);
            String description = RandomStringUtils.randomAlphabetic(20);
            Random random = new Random();
            boolean doneStatus = random.nextBoolean();
            create_todos(title, doneStatus, description);
        }

        // create projects using random generator
        for (int i = 0; i <= num; i++){
            String title = RandomStringUtils.randomAlphabetic(5);
            String description = RandomStringUtils.randomAlphabetic(20);
            Random random = new Random();
            boolean completed = random.nextBoolean();
            boolean active = random.nextBoolean();
            create_projects(title,completed, active, description);
        }

        // create categories using random generator
        for (int i = 0; i <= num; i++){
            String title = RandomStringUtils.randomAlphabetic(5);
            String description = RandomStringUtils.randomAlphabetic(20);
            create_categories(title, description);
        }

        // couple of instances created with known input values to test the functionality of the functions
        create_todos("lyx", true, "0420");  // id = 3
        create_todos("lyx", true, "0530");  // id = 4
        create_projects("the9",false, false, "lyx is the queen");   // id = 2
        create_projects("x queen",true, true, "lyx is the queen");  // id = 3
        create_categories("love", "lyx");           // id = 3
        create_categories("queen", "x queen lyx");  // id = 4

        delete_todos(3);
        delete_projects(2);
        delete_categories(3);
        
        change_todos(4,"liu", true, "love");
        change_projects(3, "liu", true, true, "0530");
        change_categories(4, "liu", "hot party");

    }
}
