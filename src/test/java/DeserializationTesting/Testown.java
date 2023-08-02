package DeserializationTesting;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import pojoClass.GetCourseData;
import static io.restassured.RestAssured.*;


public class Testown {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="http://216.10.245.166";
		
		GetData[] rData= given().queryParam("AuthorName", "Sandipan Baral").expect().defaultParser(Parser.JSON).when()
				.get("/Library/GetBook.php").then().assertThat().statusCode(200).extract().response().as(GetData[].class);
		
		
		for(int i =0;i<rData.length;i++) {
		
			
			
		System.out.println(rData[i].getBook_name());
		System.out.println(rData[i].getIsbn());
		System.out.println(rData[i].getAisle());
		
		
		
		}
	}

}
