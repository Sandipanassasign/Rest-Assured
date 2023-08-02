package JiraAPI;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.lang.System.Logger.Level;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTestE2EFlow {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "http://localhost:8080/";

		SessionFilter session = new SessionFilter();

		

		String newTokenResponse = given().relaxedHTTPSValidation().header("Content-Type", "application/json")
				.body("{ \"username\": \"SandipanBaral\", \"password\": \"@Sandy123\" }").filter(session).when()
				.post("rest/auth/1/session").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath jsT = new JsonPath(newTokenResponse);
		String TokenID = jsT.get("session.value");

		System.out.println(TokenID);
		
		// Create a new Jira ticket

		
		
		String commentmsg="This is an expected message added";
		//Create a new comment

		System.out.println("create a new comment");

		String newCommentResponse = given().relaxedHTTPSValidation().header("Content-Type", "application/json").pathParam("jiraID", "10128")
				.body("{\r\n" + "    \"body\": \""+commentmsg+"\"\r\n" + "}")
				.filter(session).when().post("rest/api/2/issue/{jiraID}/comment").then().assertThat()
				.statusCode(201).extract().response().asString();

		JsonPath js1 = new JsonPath(newCommentResponse);
		String commentID = js1.get("id");

		System.out.println(commentID);

		// Add an attachment



		String attachmentResponse = given().relaxedHTTPSValidation().header("X-Atlassian-Token", "no-check")
				.header("Content-Type", "multipart/form-data").pathParam("jiraID", "10128").filter(session)
				.multiPart("file",
						new File("E:\\New Sandipan\\eclipse-workspace-RestAssured\\DemoProject\\TestFileupload.txt"))
				.when().post("rest/api/2/issue/{jiraID}/attachments").then().assertThat().statusCode(200).extract()
				.response().asString();

	//	System.out.println(attachmentResponse);
		
		
		//get the issue details and validate the presence of comment and attachment or not
		
		
		String getIssueDetails=given().relaxedHTTPSValidation().pathParam("jiraID", "10128").filter(session).queryParam("fields", "comment") .when().get("/rest/api/2/issue/{jiraID}")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		
		System.out.println(commentID);
		
		
		JsonPath jsonp=new JsonPath(getIssueDetails);
		int size= jsonp.get("fields.comment.comments.size()");
		System.out.println(size);
		
		
		for(int i=0;i<size;i++)
		{
		String cid= jsonp.get("fields.comment.comments["+i+"].id");
		
		if(cid.equals(commentID)) {
			
			
		String commentReceived =jsonp.get("fields.comment.comments["+i+"].body");
				
		System.out.println(commentReceived);
		
		Assert.assertEquals(commentmsg, commentReceived);
		
		}
		
		
		
	}
		
		
		
			
		
		
		
		
	}

}
