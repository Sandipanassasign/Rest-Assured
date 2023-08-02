package end2endflow;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Testflow {

	public static void main(String[] args) {

		// Get access Token

		RequestSpecification resq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequestPayload login = new LoginRequestPayload();
		login.setUserEmail("baralsandipan18@yopmail.com");
		login.setUserPassword("@Sandy123");

		Response request = given().spec(resq).body(login).when().post("/api/ecom/auth/login");

		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200).build();

		LoginResponsePayload respPayload = request.then().spec(resp).extract().response()
				.as(LoginResponsePayload.class);

		String AuthToken = respPayload.getToken();
		String Userid = respPayload.getUserId();

		System.out.println(AuthToken);

		// Add a new Product

		RequestSpecification adprodresq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", AuthToken).build();

		RequestSpecification addrequest = given().spec(adprodresq).param("productName", "Air Jordan11")
				.param("productAddedBy", Userid).param("productCategory", "Fashion")
				.param("productSubCategory", "Shoes").param("productPrice", "22125")
				.param("productDescription",
						"Inspired by the original AJ1, this mid-top edition maintains the iconic look you love while choice colors and crisp leather give it a distinct identity.")
				.param("productFor", "Men").multiPart("productImage", new File(
						"E:\\New Sandipan\\eclipse-workspace-RestAssured\\DemoProject\\Resources\\e25ed3aa-127b-4313-bbde-031235047abe.jpg"));

		ResponseSpecification addprodres = new ResponseSpecBuilder().expectStatusCode(201).build();

		AddProductResponse addProdResponse = addrequest.when().post("/api/ecom/product/add-product").then()
				.spec(addprodres).log().all().extract().response().as(AddProductResponse.class);

		String ProductID = addProdResponse.getProductId();
		System.out.println(addProdResponse.getProductId());

		// Add to cart

		RequestSpecification Addcartresq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", AuthToken).build();

		ProductRequestBase RequestBase = new ProductRequestBase();
		RequestBase.set_id(Userid);

		ProductRequestDetails adddetails = new ProductRequestDetails();

		adddetails.set_id(ProductID);
		adddetails.setProductName("Air Jordan11");
		adddetails.setProductCategory("Fashion");
		adddetails.setProductSubCategory("Shoes");
		adddetails.setProductPrice("22125");
		adddetails.setProductDescription(
				"Inspired by the original AJ1, this mid-top edition maintains the iconic look you love while choice colors and crisp leather give it a distinct identity.");
		adddetails.setProductImage("https://rahulshettyacademy.com/api/ecom/uploads/productImage_1690930085812.jpg");
		adddetails.setProductRating("0");
		adddetails.setProductTotalOrders("0");
		adddetails.setProductStatus(true);
		adddetails.setProductFor("Men");
		adddetails.setProductAddedBy(Userid);
		adddetails.set__v("0");

		RequestBase.setProduct(adddetails);

		Response CartRequest = given().spec(Addcartresq).body(RequestBase).log().all().when()
				.post("/api/ecom/user/add-to-cart");

		String message = CartRequest.then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(message);

		// Post a new order

		RequestSpecification newOrderresq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", AuthToken).setContentType(ContentType.JSON).build();

		OrderDetails orderd = new OrderDetails();
		orderd.setCountry("India");
		orderd.setProductOrderedId(ProductID);

		BaseOrderDetails base = new BaseOrderDetails();

		List<OrderDetails> createOrderpayload = new ArrayList<OrderDetails>();
		createOrderpayload.add(orderd);

		base.setOrders(createOrderpayload);

		RequestSpecification postOrderRequest = given().spec(newOrderresq).body(base);

		CreateOrderResponse PostorderResponse = postOrderRequest.when().post("/api/ecom/order/create-order").then()
				.assertThat().statusCode(201).extract().response().as(CreateOrderResponse.class);

		PostorderResponse.getMessage();

		System.out.println(PostorderResponse.getOrders()[0].toString());

		String orderID = PostorderResponse.getOrders()[0].toString();

		// Get Order details

		RequestSpecification getorderResq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", AuthToken).setContentType(ContentType.JSON).addQueryParam("id", orderID)
				.build();

		String GetOrderdetails = given().spec(getorderResq).when().log().all().get("/api/ecom/order/get-orders-details")
				.then().extract().response().asString();
		System.out.println(GetOrderdetails);

		// Delete Order details

		RequestSpecification delorderResq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", AuthToken).setContentType(ContentType.JSON).build();

		given().spec(delorderResq).pathParam("OrderID", orderID).relaxedHTTPSValidation().log().all().when()
				.delete("/api/ecom/order/delete-order/{OrderID}").then().log().all().extract().response();

		// Delete product

		RequestSpecification delprodResq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", AuthToken).setContentType(ContentType.JSON).build();

		given().spec(delorderResq).pathParam("productID", ProductID).relaxedHTTPSValidation().log().all().when()
				.delete("/api/ecom/product/delete-product/{productID}").then().log().all().extract().response();

		System.out.println("E2E testing completed");

	}

}
