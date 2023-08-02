package RestAssured.DemoProject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import testData.Payloads;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class demoAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Post Add Map
		String response= given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payloads.AddPlace()).when().log().all().post("maps/api/place/add/json").
		then().log().all().assertThat().statusCode(200).header("Server", "Apache/2.4.52 (Ubuntu)").header("Access-Control-Allow-Methods", "POST")
		.body("scope", equalTo("APP")).extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String placeID=js.getString("place_id");
		
		System.out.println(placeID);
		
		//Put Update Map
		String newAddress="111 North Market Street , Suite 300";
		String PutResponse= given().queryParam("key","qaclick123" ).body("{\r\n"
				+ "    \"place_id\": \""+placeID+"\",\r\n"
				+ "    \"address\": \""+newAddress+"\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().assertThat().statusCode(200).
				body("msg", equalTo("Address successfully updated")).extract().response().asString();
		
		System.out.println(PutResponse);
		
		
		//Get Map
		
		String GetResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID).when().get("maps/api/place/get/json").then()
		.assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(GetResponse);
		
		
		JsonPath jsaddress=new JsonPath(GetResponse);
		String getAddress=jsaddress.getString("address");
		
		System.out.println(getAddress);
		
		
		Assert.assertEquals(getAddress,newAddress);
		
		
	}

}
