package JiraAPI;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.lang.System.Logger.Level;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTestwithNewTicketeverytime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "http://localhost:8080/";

		SessionFilter session = new SessionFilter();

		// Create a new Jira ticket

		String newTokenResponse = given().header("Content-Type", "application/json")
				.body("{ \"username\": \"SandipanBaral\", \"password\": \"@Sandy123\" }").filter(session).when()
				.post("rest/auth/1/session").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath jsT = new JsonPath(newTokenResponse);
		String TokenID = jsT.get("session.value");

		System.out.println(TokenID);

		String newTicketResponse = given().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n"
				+ "          \"key\": \"CAN\"\r\n" + "       },\r\n"
				+ "       \"summary\": \"Creating an Bug Ticket from Rest Assured by Sandipan\",\r\n"
				+ "       \"description\": \"Creating of an Task using project keys and Task type names using the REST "
				+ "API by Sandipan \",\r\n" + "       \"issuetype\": {\r\n" + "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n" + "   }\r\n" + "}").filter(session).when().post("rest/api/2/issue/").then()
				.assertThat().statusCode(201).extract().response().asString();

		JsonPath js = new JsonPath(newTicketResponse);
		String jiraID = js.get("id");

		JsonPath js2 = new JsonPath(newTicketResponse);
		String Key = js2.get("key");

		System.out.println(jiraID);
		System.out.println(Key);
		
		
		//Create a new comment

		System.out.println("create a new comment");

		String newCommentResponse = given().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \"This is a comment 1 by Sandipan from rest Assured code.\"\r\n" + "}")
				.filter(session).when().post("rest/api/2/issue/" + jiraID + "/comment").then().assertThat()
				.statusCode(201).extract().response().asString();

		JsonPath js1 = new JsonPath(newCommentResponse);
		String commentID = js1.get("id");

		System.out.println(commentID);

		// Add an attachment

		// curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F
		// "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments

		String attachmentResponse = given().header("X-Atlassian-Token", "no-check")
				.header("Content-Type", "multipart/form-data").filter(session)
				.multiPart("file",
						new File("E:\\New Sandipan\\eclipse-workspace-RestAssured\\DemoProject\\TestFileupload.txt"))
				.when().post("rest/api/2/issue/" + Key + "/attachments").then().assertThat().statusCode(200).extract()
				.response().asString();

	//	System.out.println(attachmentResponse);
		
		
		//get the issue details and validate the presence of comment and attachment or not
		
		
		String getIssueDetails=given().filter(session).queryParam("fields", "comment") .when().get("/rest/api/2/issue/"+Key+"")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		
		
		
		
		JsonPath jsonp=new JsonPath(getIssueDetails);
		String cid= jsonp.get("fields.comment.comments[0].id");
		
		System.out.println(cid);
			
		
		Assert.assertEquals(cid, commentID);
		
	}

}
