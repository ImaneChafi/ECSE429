import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
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

public class Docs {

	//////////////////See if server has started////////////////////////////////////////////////////

	@Before
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
