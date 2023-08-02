package libraryAPI;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class PostAddBookwithExternalFile {

	@Test
	public void PostAdd() throws IOException {

		RestAssured.baseURI = "http://216.10.245.166";
		String postAddResonse = given().header("Content-Type", "application/json")
				.body(jsonfileread(
						"E:\\New Sandipan\\eclipse-workspace-RestAssured\\DemoProject\\Resources\\AddBookPayload.json"))
				.when().post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(postAddResonse);
		String ResponseID = js.get("ID").toString();

		System.out.println(ResponseID);

		String postDelBook = given().header("Content-Type", "application/json")
				.body("{\r\n" + "\"ID\" : \"" + ResponseID + "\"\r\n" + "} ").when().post("Library/DeleteBook.php")
				.then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(postDelBook);

		JsonPath js1 = new JsonPath(postDelBook);
		String ResponseID1 = js1.get("msg").toString();

		System.out.println(ResponseID1);

	}

	public static String jsonfileread(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));

	}

}
