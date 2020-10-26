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

public class Shutdown {
	 /*
    GET /shutdown
    success, the server should crash and the test should not pass 
     */
    @Test
    public void test_shutdown() throws IOException, InterruptedException {
    	HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/shutdown")).GET()
                .build();
        try {
            // create a new HttpClient
            HttpClient client_test_is_running = HttpClient.newHttpClient();
            // build a new HttpRequest
            HttpRequest request_test_is_running = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:4567"))
                    .GET().build(); // GET is default
            
            HttpResponse<Void> response = client_test_is_running.send(request_test_is_running, HttpResponse.BodyHandlers.discarding());
            assertEquals(404, response.statusCode());
        } catch (IOException e) {
            final JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "The server has been successfully closed.");
            exit(-1);
        }
       
       }

}
