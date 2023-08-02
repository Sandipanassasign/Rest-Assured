package oauth2Authentication;


import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
public class oauth2 {

	public static void main(String[] args) throws InterruptedException {
	
		
		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AZEOvhUy6z501R9KAK2VMiSRIeKUOiRyEbziygnFfY6jB6AHJ6gkgJ_NvVJvn7XEtlkVDQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=1&prompt=none";
		
		
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
	
		
		String fetchCourese= given().queryParam("access_token", accessToken).when()
		.get("/getCourse.php").then().log()
		.all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(fetchCourese);
		
		

	}

}
