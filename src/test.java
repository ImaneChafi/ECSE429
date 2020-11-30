import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class test {
	public static void create_todos(String title, boolean doneStatus, String description) throws Exception {
		long start_t1 = System.nanoTime();   
		var todo = new HashMap<String, Object>() {{
			put("title", title);
			put("doneStatus", doneStatus);
			put("description", description);
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(todo);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//	        System.out.println(response.body());
		long T2_end = System.nanoTime();
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("\nT1 for test_todos_post: " + T1);
		
		System.out.print("\nT2 for test_todos_post: " + T2);
		System.out.print("\n");
	}

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





		// couple of instances created with known input values
		create_todos("lyx", true, "0420");
		

	}
}
