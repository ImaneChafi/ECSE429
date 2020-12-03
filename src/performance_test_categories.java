import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
public class performance_test_categories {
	/**
	 * helper method to create categories
	 * @param title title of the categories you want to create
	 * @param description a brief description of the categories
	 * @throws Exception
	 */
	public static void create_categories(String title, String description) throws Exception {
		long start_t1 = System.nanoTime(); 
		var todo = new HashMap<String, Object>() {{
			put("title", title);
			put("description", description);
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(todo);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories"))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		//System.out.print("\nT1 for test_categiries_post: " + T1);//

		System.out.print("\nT2 for test_categories_post: " + T2);//
		System.out.print("\n");
	}

	/**
	 * helper method to delete categories with id
	 * @param id the id of the categories you want to delete
	 * @throws Exception
	 */
	public static void delete_categories(int id) throws Exception {
		long start_t1 = System.nanoTime();
		long start_t2 = System.nanoTime();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:4567/categories/"+id))
				.DELETE().build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();
		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		//System.out.print("\nT1 for test_categories_delete: " + T1);
		System.out.print("\nT2 for test_categories_delete: " + T2);
		System.out.print("\n");
	}

	/**
	 * helper method to update/change categories
	 * @param id the id of the categories you want to change
	 * @param title the title you would like to change with
	 * @param description the description you would like to change with
	 * @throws Exception
	 */
	public static void change_categories(int id, String title, String description) throws Exception {
		long start_t1 = System.nanoTime();
		var todo = new HashMap<String, Object>() {{
			put("title", title);
			put("description", description);
		}};
		long start_t2 = System.nanoTime();
		var om = new ObjectMapper();
		String requestBody = om.writeValueAsString(todo);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/"+id))
				.PUT(HttpRequest.BodyPublishers.ofString(requestBody)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		//        System.out.println(response.body());
		long T2_end = System.nanoTime();

		long T1_end = System.nanoTime();
		long T1 = (T1_end - start_t1);
		long T2 = (T2_end - start_t2);

		//Printing the statements to have a record of the performance time
		//System.out.print("\nT1 for test_categories_put_works: " + T1);
		System.out.print("\nT2 for test_categories_put_works: " + T2);
		System.out.print("\n");
	}

	/**
	 * populate the database for todos, projects, and categories using the methods defined above
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// testing with 10 values

		int num = 10;

		System.out.println("Creating " + num + " categories");
		// create categories using random generator
		for (int i = 0; i <= num; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			create_categories(title, description);
		}
		// testing with 100 values
		int num1 = 100;
		System.out.print("\n");
		System.out.println("Creating " + num1 + " categories");
		// create categories using random generator
		for (int i = 0; i <= num1; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			create_categories(title, description);
		}
		// testing with 1000 values
		int num2 = 1000;
		System.out.print("\n");
		System.out.println("Creating " + num2 + " categories");
		// create categories using random generator
		for (int i = 0; i <= num2; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			create_categories(title, description);
		}
		// testing with 10000 values
		int num3 = 10000;
		System.out.print("\n");
		System.out.println("Creating " + num3 + " categories");
		// create categories using random generator
		for (int i = 0; i <= num3; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			create_categories(title, description);
		}

		// testing with 100000 values
		int num4 = 100000;
		System.out.print("\n");
		System.out.println("Creating " + num4 + " categories");
		// create categories using random generator
		for (int i = 0; i <= num4; i++){
			String title = RandomStringUtils.randomAlphabetic(5);
			String description = RandomStringUtils.randomAlphabetic(20);
			create_categories(title, description);
		}
		// couple of instances created with known input values to test the functionality of the functions

		create_categories("love", "lyx");           // id = 3
		//create_categories("queen", "x queen lyx");  // id = 4


		delete_categories(3);


		change_categories(4, "liu", "hot party");

	}
}
