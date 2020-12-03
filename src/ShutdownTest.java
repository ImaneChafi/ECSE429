import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import org.junit.*;
import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.*;

public class ShutdownTest {
	 /*
    GET /shutdown
    Failure, this test is expected to fail if the server has been stopped, the server should crash and the test should not pass 
   */
    @Test
    public void test_todos_get() throws IOException, InterruptedException {
    	long start_t1 = System.nanoTime();
    	long start_t2 = System.nanoTime();
    	HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:4567/shutdown")).GET()
                .build();
        HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
        long T2_end = System.nanoTime();
        assertEquals(200,response.statusCode());
        System.out.println(response.body());
    	
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);
				
		//Printing the statements to have a record of the performance time
		System.out.print("T1 for test_todos_get: " + T1);
		System.out.print("T2 for test_todos_get: " + T2);

    }
}
