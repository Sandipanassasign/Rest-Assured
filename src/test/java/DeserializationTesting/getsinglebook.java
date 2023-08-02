package DeserializationTesting;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.*;
public class getsinglebook {
	
	@Test
	public void SingleBook() {
		
		 RestAssured.baseURI="https://simple-books-api.glitch.me";
		
		 bookPojoRes res = given().log().all().when().get("/books/5	").then().log().all()
		.assertThat().statusCode(200).extract().response().as(bookPojoRes.class);
		
	
		 System.out.println(res.getAuthor()); 
		 System.out.println(res.getAvailable()); 
		 System.out.println(res.getId()); 
		 
		 
		 
	}

}
