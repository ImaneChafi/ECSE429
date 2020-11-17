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

public class ShutdownTest {
	 /*
    GET /shutdown
    Failure, this test is expected to fail if the server has been stopped, the server should crash and the test should not pass 
   */
    @Test
    public void test_todos_get() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/shutdown")).GET()
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        assertEquals(200,response.statusCode());
        System.out.println(response.body());
    }
}
