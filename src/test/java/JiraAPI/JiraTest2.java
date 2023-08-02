package JiraAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import jdk.internal.net.http.common.Log;

import static io.restassured.RestAssured.*;
public class JiraTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		RestAssured.baseURI="http://localhost:8080/";
		
		
		String SessionID="JSESSIONID=C16532A9616D66CA5B7A52C754AAB5C3; atlassian.xsrf.token=BYOU-93MA-D3Y1-0YJQ_57e369de8b82feb0e2b8a35ad4c70b518520a6a8_lin";
		
		
		//Create a new Jira ticket
		
		
		String newTokenResponse= given().log().all().header("Cookie",SessionID).header("Content-Type","application/json").body("{ \"username\": \"SandipanBaral\", \"password\": \"@Sandy123\" }")
		.when().log().all().post("rest/auth/1/session").then().assertThat().statusCode(200).extract().response().asString();
		

		JsonPath jsT=new JsonPath(newTokenResponse);
		String TokenID=jsT.get("session.value");
		
		System.out.println(TokenID);
		
		
		
		String newTicketResponse=given().log().all().header("Cookie",SessionID).header("Content-Type","application/json").
		body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"CAN\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Creating an Task from PostMan by Sandipan\",\r\n"
				+ "       \"description\": \"Creating of an Task using project keys and Task type names using the REST API by Sandipan \",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Task\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}").when().log().all().post("rest/api/2/issue/").then().log().all().assertThat().statusCode(201).extract().response().asString();
	
		
		
		JsonPath js=new JsonPath(newTicketResponse);
		String jiraID=js.get("id");
		
		System.out.println(jiraID);
	
		
		//create a new comment
		
		String newCommentResponse= given().log().all().header("Cookie",SessionID).header("Content-Type","application/json").
		body("{\r\n"
				+ "    \"body\": \"This is a comment 1 by Candy from rest Assured code.\"\r\n"
				+ "}").when().log().all().post("rest/api/2/issue/"+jiraID+"/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
	
		JsonPath js1=new JsonPath(newCommentResponse);
		String commentID=js1.get("id");
		
		System.out.println(commentID);
	}

}
