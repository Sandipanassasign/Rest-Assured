package pojoPractice;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import com.google.gson.JsonObject;

public class GetBookNameByID {

	public static void main(String[] args) {
		
		RestAssured.baseURI="http://216.10.245.166";
		
		PojoResponse[] reps= given().queryParam("ID", "Sand440").expect().defaultParser(Parser.JSON).when().get("/Library/GetBook.php").then().extract()
		.response().as(PojoResponse[].class);
		
		System.out.println(reps.length); 
		System.out.println(reps[0].getBook_name());
		
	/*
		
		PojoAddResponse presponse= given().body("{\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\"Sand\",\r\n"
				+ "\"aisle\":\"41140\",\r\n"
				+ "\"author\":\"Sandipan Baral\"\r\n"
				+ "}\r\n"
				+ "").expect().defaultParser(Parser.JSON).when().post("/Library/Addbook.php").then().extract()
		.response().as(PojoAddResponse.class);
		
	System.out.println(presponse.getID());	
	System.out.println(presponse.getMsg());	
	 */
		
		
		PUTMSG mst=	given().queryParam("key", "qaclick123").body("{\r\n"
				+ "    \"place_id\": \"0449643ee7239597408b370338941f54\",\r\n"
				+ "    \"address\": \"31, side wise layout, cohen 09\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}").expect().defaultParser(Parser.JSON).when().put("http://rahulshettyacademy.com/maps/api/place/update/json").then()
		.extract().response().as(PUTMSG.class);
		
		System.out.println(mst.getMsg());

		
	
	}

}
