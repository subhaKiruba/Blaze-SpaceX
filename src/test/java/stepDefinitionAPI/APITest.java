package stepDefinitionAPI;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APITest {
	static Logger log = Logger.getLogger(APITest.class.getName());

	private static final String BASE_URL = "https://api.spacexdata.com/v4/launches/latest";

	private static JSONObject json;
	private static JSONArray jsonArr;
	private static Response response;

	@Given("I validate the status of GET call")
	public void validateGETCall() {

		Assert.assertTrue("NOT a valid URL", isUrlValid(BASE_URL));
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get(RestAssured.baseURI);
		Assert.assertEquals(200, response.getStatusCode());
	}

	/*
	 * validates whether give rest url is valid
	 */
	public static boolean isUrlValid(String url) {
		try {
			URL obj = new URL(url);
			obj.toURI();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		}
	}

	@Given("I validate the api response time")
	public void validateResponseTime() {
		log.info("Reponse Time::" + response.getTimeIn(TimeUnit.SECONDS));
	}

	/*
	 * Method : validateAPIKeysAndDataTypes validates the apikeys and its
	 * dataTypes from the respose
	 */
	@Then("I validate api keys and its dataType in the response")
	public void validateAPIKeysAndDataTypes() {
		log.info("Reponse Body::" + response.getBody().asString());

		json = new JSONObject(response.getBody().asString());
		Iterator<String> iterator = json.keys();

		if (iterator != null) {
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = json.get(key);
				String dataType = value.getClass().getSimpleName();

				if (dataType.equalsIgnoreCase("Integer")) {
					log.info("Integer Key :" + key + " | type :int | value:" + value);

				} else if (dataType.equalsIgnoreCase("Long")) {
					log.info("Long Key :" + key + " | type :long | value:" + value);

				} else if (dataType.equalsIgnoreCase("Float")) {
					log.info("Float Key :" + key + " | type :float | value:" + value);

				} else if (dataType.equalsIgnoreCase("Double")) {
					log.info("Double Key :" + key + " | type :double | value:" + value);

				} else if (dataType.equalsIgnoreCase("Boolean")) {
					log.info("Boolean Key :" + key + " | type :bool | value:" + value);

				} else if (dataType.equalsIgnoreCase("String")) {
					log.info("String Key :" + key + " | type :string | value:" + value);

				}
			}
		}

	}

	@Given("I validated spaceX mission is success")
	public void isMissionSuccess() {
		Assert.assertTrue("Step not success", response.jsonPath().get("success"));
	}

	/*
	 * Method : validateEntities param : entity this method is to validate
	 * entities containing JSONArray/Object crew and capsules are not validated
	 * since its actual itself is null
	 */
	@Given("I validate the array \"([^\"]*)\" values from the api response")
	public void validateEntities(String entity) {

		json = new JSONObject(response.getBody().asString());
		Iterator<String> iterator = json.keys();

		if (iterator != null) {
			while (iterator.hasNext()) {
				String key = iterator.next();
				// if(key.equals("crew")){
				// log.info("crew::"+json.get(key));
				//
				// }
				if (key.equals("ships")) {
					jsonArr = new JSONArray();
					jsonArr.put("5ea6ed2e080df4000697c909");
					jsonArr.put("5ea6ed2f080df4000697c90c");
					jsonArr.put("5ea6ed2f080df4000697c90d");
					jsonArr.put("5ea6ed30080df4000697c913");

					Assert.assertTrue("Ships values are not equal",
							(json.getJSONArray(key).toString()).equals(jsonArr.toString()));
				}

				// if(key.equals("capsules")){
				// log.info("capsules::"+json.get(key));
				// //Assert.assertArrayEquals(expecteds, json.get(key));
				// }
				if (key.equals("payloads")) {
					jsonArr = new JSONArray();
					jsonArr.put("600f9bd88f798e2a4d5f97a6");
					Assert.assertTrue("Payload values are not equal",
							(json.getJSONArray(key).toString()).equals(jsonArr.toString()));
				}
				if (key.equals("cores")) {
					JSONArray obj = json.getJSONArray(key);
					List<Object> jsonList = obj.toList();

					for (Object tempObj : jsonList) {
						HashMap jsonObj = (HashMap) tempObj;
						Assert.assertEquals("5e9e28a6f35918c0803b265c", jsonObj.get("core"));
						Assert.assertEquals(9, jsonObj.get("flight"));
						Assert.assertEquals("ASDS", jsonObj.get("landing_type"));
						Assert.assertEquals(true, jsonObj.get("gridfins"));
						Assert.assertEquals(true, jsonObj.get("landing_attempt"));
						Assert.assertEquals(true, jsonObj.get("legs"));
						Assert.assertEquals("5e9e3032383ecb6bb234e7ca", jsonObj.get("landpad"));
						Assert.assertEquals(true, jsonObj.get("reused"));
						Assert.assertEquals(true, jsonObj.get("landing_success"));
					}

				}

			}
		}
	}

	/*
	 * Method : validate404Response this method is to validate response code for
	 * invalid string in the url
	 */

	@Given("I validate the status for invalid entity")
	public void validate404Response() {
		RestAssured.baseURI = "https://api.spacexdata.com/v4/launches/lates";
		RequestSpecification request = RestAssured.given();
		response = request.get(RestAssured.baseURI);
		Assert.assertEquals(404, response.getStatusCode());
	}

	/*
	 * Method : validateInvalidHttpCall Validates response code if a invalid
	 * http call is made
	 */

	@Given("I validate the response for invalid http method call")
	public void validateInvalidHttpCall() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.post(RestAssured.baseURI);
		Assert.assertEquals(404, response.getStatusCode());
	}

}