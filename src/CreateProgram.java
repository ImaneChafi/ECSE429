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

        // couple of instances created with known input values
        create_todos("lyx", true, "0420");
        create_todos("lyx", true, "0530");
        create_projects("the9",false, false, "lyx is the queen");
        create_projects("x queen",true, true, "lyx is the queen");
        create_categories("love", "lyx");
        create_categories("queen", "x queen lyx");
    }
}
