package Serialization;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class addMaps {

	public static void main(String[] args) {
		
		
		pojoAddMap pj=new pojoAddMap();
		pj.setAccuracy(50);
		pj.setName("Frontline house");
		pj.setPhone_number("(+91) 983 893 3937");
		pj.setAddress("29, side layout, cohen 09");
		pj.setWebsite("http://google.com");
		pj.setLanguage("French-IN");
		
		Location loc=new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		
		pj.setLocation(loc);
		
		List<String> lt=new ArrayList<String>();
		lt.add("shoe park");
		lt.add("shop");
		
		pj.setTypes(lt);
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		
		mapsResponse res=given().queryParam("key", "qaclick123").body(pj).log().all().expect().defaultParser(Parser.JSON).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).log().all().extract().response().as(mapsResponse.class);
		
		
		System.out.println("_______________________");
		
		System.out.println(res.getPlace_id()); 
		System.out.println(res.getId()); 
		
		
		
		
		
		
		
		
		

	}

}
