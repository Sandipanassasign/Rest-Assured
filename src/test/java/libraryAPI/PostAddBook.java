package libraryAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import testData.Payloads;

import static io.restassured.RestAssured.*;

public class PostAddBook {

	@Test(dataProvider = "bookdata")
	public void PostAdd(String isbn, String ID) {

		RestAssured.baseURI = "http://216.10.245.166";
		String postAddResonse = given().header("Content-Type", "application/json").body(Payloads.AddBook(isbn, ID))
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

	@DataProvider(name = "bookdata")
	public Object[][] getData() {

		return new Object[][] { { "Sam", "4754" }, { "Pro", "22525" }, { "luxor", "5251" }, { "bother", "12312" },
				{ "Begore", "54354" }, { "Martha", "9745" }, { "Venice", "3698" }, { "Charlotte", "232" },
				{ "Kevin", "2314" } };

	}

}
