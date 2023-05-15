package com.test.specAndSchemaTest;
import com.google.gson.Gson;
import com.test.specAndSchemaTest.JsonData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Set;
import org.testng.annotations.Test;
public class APIspecAndSchemaTest {

	@Test
	public static void verifyAPIResponse() {
		String projectPath = System.getProperty("user.dir");

		String filePath = projectPath + "/src/test/resources/testApi.json";

		JsonData jsonData = JsonData.getInstance();

		// Load the JSON data
		jsonData.loadJsonData(filePath);
		// Get the names of all data types and verify response
		Set<String> dataTypes = jsonData.getDataTypes();
		
		if (dataTypes != null) {
			for (String dataType : dataTypes) {
				System.out.println("Data Type: " + dataType);
				System.out.println(jsonData.getResponseData(dataType));
				// verifying response
				verifyAPIResponse(dataType, jsonData.getRequestURL(dataType), jsonData.getType(dataType),
						jsonData.getResponse(dataType), jsonData.getResponseData(dataType));
			}
		} else {
			System.out.println("No data types found");
		}

	}

	private static void verifyAPIResponse(String endpointName, String requestURL, String requestType,
			Double expectedResponseCode, Object expectedResponseData) {
		Response response = RestAssured.get("https://reqres.in" + requestURL);

		// Verify response code
		int actualResponseCode = response.getStatusCode();
		System.out.println("entered here");
		if (actualResponseCode == expectedResponseCode) {
			System.out.println("Response code for " + endpointName + " is correct.");
		} else {
			System.out.println("Response code for " + endpointName + " is incorrect. Expected: " + expectedResponseCode
					+ ", Actual: " + actualResponseCode);
		}

		// Verify response data
		String responseBody = response.getBody().asString();
		if (!responseBody.isEmpty()) {
			// System.out.println(responseBody);
			System.out.println("Response data for " + endpointName + " is not empty.");
		} else {
			System.out.println("Response data for " + endpointName + " is empty.");
		}

		// Verify response data format (assuming expectedResponseData is of type Object)
		if (expectedResponseData != null && expectedResponseData instanceof Object) {

			Gson gson = new Gson();

			// ...
			String expectedResponseDataFromFile = gson.toJson(expectedResponseData);
			String newdata = expectedResponseDataFromFile.replaceAll("(\"https:[^\"]*\")|([^:\\s])\\s*:", "$1$2=").replaceAll(",", ", ").replace("\"", "");
			//System.out.println("new data is : "+newdata);
			System.out.println("expectedResponseDataFromFile: " + newdata);

			Object actualResponseData = response.getBody().as(Object.class);
			System.out.println("actualResponseData :" + actualResponseData);

			if (actualResponseData.equals(newdata)) {
				System.out.println("Response data for " + endpointName + " matches the expected format.");
			} else {
				System.out.println("Response data for " + endpointName + " does not match the expected format.");
			}
		} else {
			System.out.println("No response data format verification provided for " + endpointName + ".");
		}

	}
}
