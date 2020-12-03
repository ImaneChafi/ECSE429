import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
public class performance_test_projects {


	/**
	 * helper method to create projects
	 * @param title title of the project you want to create
	 * @param completed completion status of the project
	 * @param active active status of the project
	 * @param description a brief description of the project
	 * @throws Exception
	 */
	public static void create_projects(String title, boolean completed, boolean active, String description) throws Exception {
		long start_t1 = System.nanoTime(); 
		var todo = new HashMap<String, Object>() {{
			put("title", title);
			put("completed", completed);
			put("active", active);
			put("description", description);
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(todo);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("\nT1 for test_projects_post: " + T1);

		System.out.print("\nT2 for test_projects_post: " + T2);//
		System.out.print("\n");

	}

	/**
	 * helper method to delete projects with id
	 * @param id the id of the projects you want to delete
	 * @throws Exception
	 */
	public static void delete_projects(int id) throws Exception {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/projects/"+id))
				.DELETE().build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("\nT1 for test_projects_delete: " + T1);
		System.out.print("\nT2 for test_projects_delete: " + T2);
		System.out.print("\n");
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
		long start_t1 = System.nanoTime();
		var todo = new HashMap<String, Object>() {{
			put("title", title);
			put("completed", completed);
			put("active", active);
			put("description", description);
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(todo);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/"+id))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		System.out.print("\nT1 for test_prijects_put_works: " + T1);
		System.out.print("\nT2 for test_projects_put_works: " + T2);
		System.out.print("\n");
	}

	/**
	 * populate the database for projects using the methods defined above
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {



		int num = 10;       // number of instances to generate, you can change the number to test the performance

		// create projects using random generator
		for (int i = 0; i <= num; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean completed = random.nextBoolean();
			boolean active = random.nextBoolean();
			create_projects(title,completed, active, description);
		}

		int num1 = 100;       // number of instances to generate, you can change the number to test the performance

		// create projects using random generator
		for (int i = 0; i <= num1; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean completed = random.nextBoolean();
			boolean active = random.nextBoolean();
			create_projects(title,completed, active, description);
		}

		int num2 = 1000;       // number of instances to generate, you can change the number to test the performance

		// create projects using random generator
		for (int i = 0; i <= num2; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean completed = random.nextBoolean();
			boolean active = random.nextBoolean();
			create_projects(title,completed, active, description);
		}

		int num3 = 10000;       // number of instances to generate, you can change the number to test the performance

		// create projects using random generator
		for (int i = 0; i <= num3; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean completed = random.nextBoolean();
			boolean active = random.nextBoolean();
			create_projects(title,completed, active, description);
		}

		int num4 = 20000;       // number of instances to generate, you can change the number to test the performance

		// create projects using random generator
		for (int i = 0; i <= num4; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			Random random = new Random();
			boolean completed = random.nextBoolean();
			boolean active = random.nextBoolean();
			create_projects(title,completed, active, description);
		}
		create_projects("x queen",true, true, "lyx is the queen");  // id = 3
		delete_projects(2);
		change_projects(3, "liu", true, true, "0530");


	}
}
