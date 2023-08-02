package pojoClass;


import static io.restassured.RestAssured.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
public class deserialization {

	@Test
	public void GetData() throws InterruptedException {
	
		
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AZEOvhW7Izxnh2cD33w1RTP6TbYzA58fdkxdggFM157NYqk_GzmWPq7QNAkGv69l7MzS7w&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		
		
		String[] part2=url.split("code=");
		String[] part1= part2[1].split("&scope");
		String authCode=part1[0].trim();
		
		System.out.println(authCode);
		
		
		
		//driver.close();
		
		String GetAccessTokenResponse=given().urlEncodingEnabled(false).queryParams("code", authCode)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.log().all().when().post("https://www.googleapis.com/oauth2/v4/token").then().extract().response().asString();
		
		JsonPath js=new JsonPath(GetAccessTokenResponse);
		String accessToken=js.getString("access_token");
		
		System.out.println("Access Token Generated is : "+accessToken);
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
	
		
		GetCourseData responseData= given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
		.get("/getCourse.php").then().assertThat().statusCode(200).extract().response().as(GetCourseData.class);
		/*
		System.out.println(responseData.getExpertise());
		System.out.println(responseData.getInstructor());
		System.out.println(responseData.getLinkedIn());
		System.out.println(responseData.getServices());
		System.out.println(responseData.getUrl());
		 
		 */
		List<api> allAPI=responseData.getCourses().getApi();
		
		int size=allAPI.size();
		
		for(int i=0;i<size;i++) {
			
			if(allAPI.get(i).getCourseTitle().contains("SoapUI ")) {
				
				System.out.println(allAPI.get(i).getPrice()); 
				break;
			}
			
		}
		
		//Get All Course titles of WebAutomation
		
		List<WebAutomation> AllWeb=responseData.getCourses().getWebAutomation();
		
		int sizeweb=AllWeb.size();
		
		for(int i=0;i<sizeweb;i++) {
			
			
		System.out.println(AllWeb.get(i).getCourseTitle());	
			
			
		}
	}

}
