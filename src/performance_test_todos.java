import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class performance_test_todos {
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

		System.out.print("\nT2 for test_todos_post: " + T2);//
		System.out.print("\n");
	}
	/**
	 * helper method to delete a todos with id
	 * @param id the id of the todos you want to delete
	 * @throws Exception
	 */
	public static void delete_todos(int id) throws Exception {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/todos/"+id))
				.DELETE().build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("\nT1 for test_todo_id_delete: " + T1);
		System.out.print("\nT2 for test_todo_id_delete: " + T2);
		System.out.print("\n");
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
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/"+id))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("\nT1 for test_todos_put_works: " + T1);
		System.out.print("\nT2 for test_todos_id_put_works: " + T2);
		System.out.print("\n");
	}
	public static void main(String[] args) throws Exception {

		int num = 10;       // number of instances to generate, you can change the number to test the performance
		// create todos using random generator
		for (int i = 0; i <= num; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean doneStatus = random.nextBoolean();
			create_todos(title, doneStatus, description);
		}

		int num1 = 100;       // number of instances to generate, you can change the number to test the performance
		// create todos using random generator
		for (int i = 0; i <= num1; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean doneStatus = random.nextBoolean();
			create_todos(title, doneStatus, description);
		}

		int num2 = 1000;       // number of instances to generate, you can change the number to test the performance
		// create todos using random generator
		for (int i = 0; i <= num2; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean doneStatus = random.nextBoolean();
			create_todos(title, doneStatus, description);
		}

		int num3 = 10000;       // number of instances to generate, you can change the number to test the performance
		// create todos using random generator
		for (int i = 0; i <= num3; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean doneStatus = random.nextBoolean();
			create_todos(title, doneStatus, description);
		}

		int num4 = 20000;       // number of instances to generate, you can change the number to test the performance
		// create todos using random generator
		for (int i = 0; i <= num4; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean doneStatus = random.nextBoolean();
			create_todos(title, doneStatus, description);
		}





		// couple of instances created with known input values
		create_todos("lyx", true, "0420");
		delete_todos(3);
		change_todos(4,"liu", true, "love");


	}
}
