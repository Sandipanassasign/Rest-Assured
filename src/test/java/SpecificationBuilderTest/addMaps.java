package SpecificationBuilderTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class addMaps {

	public static void main(String[] args) {

		pojoAddMap pj = new pojoAddMap();
		pj.setAccuracy(50);
		pj.setName("Frontline house");
		pj.setPhone_number("(+91) 983 893 3937");
		pj.setAddress("29, side layout, cohen 09");
		pj.setWebsite("http://google.com");
		pj.setLanguage("French-IN");

		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);

		pj.setLocation(loc);

		List<String> lt = new ArrayList<String>();
		lt.add("shoe park");
		lt.add("shop");
		pj.setTypes(lt);

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		RequestSpecification request = given().spec(req).body(pj);
		
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		mapsResponse rcvdResponse = request.log().all().when().post("/maps/api/place/add/json").then().spec(res).log()
				.all().extract().response().as(mapsResponse.class);

		System.out.println("_______________________");

		System.out.println(rcvdResponse.getPlace_id());
		System.out.println(rcvdResponse.getId());

	}

}
