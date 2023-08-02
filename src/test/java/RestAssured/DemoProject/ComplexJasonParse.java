package RestAssured.DemoProject;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import testData.MockAPI;

public class ComplexJasonParse {

	public static void main(String[] args) {
	
		JsonPath js=new JsonPath(MockAPI.dummyAPI());
		
		//1. Print No of courses returned by API
				
		int coursesize = js.getInt("courses.size()");
		System.out.println(coursesize);
		
		//2.Print Purchase Amount
		
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		String website=js.get("dashboard.website");
		System.out.println(website);
		
		
		//3. Print Title of the first course
		
		String titlefirstcource= js.get("courses[0].title");
		System.out.println(titlefirstcource);
		
		//4. Print All course titles and their respective Prices
		
		for(int i=0;i<coursesize;i++) {
			
			String allTitles= js.get("courses["+i+"].title");
			
			
			int allPrices= js.get("courses["+i+"].price");
			System.out.println(allTitles+" - "+allPrices);
			
			
			
			
		}
				
		//5. Print no of copies sold by RPA Course
		
		for(int i=0;i<coursesize;i++) {
			
			if(js.get("courses["+i+"].title").equals("RPA")) {
				
				int RPACopies= js.get("courses["+i+"].copies");
				System.out.println(RPACopies);
				
			}
			
		}
		
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount

		int sum=0;
		for(int i=0;i<coursesize;i++) {
			
			int pr=js.get("courses["+i+"].price");
			int copies=js.get("courses["+i+"].copies");
			
			sum=sum+(pr*copies);
			
			
			
		}
		System.out.println(sum);
		
		int pru=js.getInt("dashboard.purchaseAmount");
		
		Assert.assertEquals(sum, pru);
		
		
		
		
	
	}

}
