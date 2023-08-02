package JsonValidation;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;



public class JsonValidate {
	
	@Test
	public void validate() {
		
		RestAssured.baseURI="https://reqres.in/";
		
		given()
		.get("users?page=2")
		.then()
		.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("products-schema.json")).statusCode(200).log().all();;
		
		
		
		
	}

}
