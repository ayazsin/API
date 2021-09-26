package patch_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import test_data.JsonPlaceHolderTestData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class Patch01 extends JsonPlaceHolderBaseUrl {

    /*
        Given
            https://jsonplaceholder.typicode.com/todos/198

            {
                "userId": 21,
                "title": "Wash the dishes",
                "completed": false
            }
        When
            I send PATCH Request to the Url

         Then
           Status code is 200
           And response body is like   {
                                        "userId": 10,
                                        "title": "Wash the dishes",
                                        "completed": true,
                                        "id":198
                                       }
    */

    @Test
    public void patch01() {


        //!.Step: Set the URL
        spec.pathParams("first", "todos", "second", 198);

        //2.Step: Set the request body data
        JsonPlaceHolderTestData requestBody = new JsonPlaceHolderTestData();
        Map <String, Object> requestBodyMap = requestBody.expectedDataSetUpWithMissingKeys(null, "Wash the dishes", null);
        System.out.println(requestBodyMap);

        //3.Step: Send the request and get the reponse
        Response response = given().spec(spec).contentType(ContentType.JSON).body(requestBodyMap).when().patch("/{first}/{second}");
        response.prettyPrint();

        //4.Step: Do assertion

        //1.Logic: No need to verify data if you didn't touch
        response.then().assertThat().statusCode(200).body("title",equalTo(requestBodyMap.get("title"))); //Add static method for equalTo "hamcrest"

        //2.Logic:
        Map<String, Object> expectedDataMap = requestBody.expectedDataSetUpWithAllKeys(10,"Wash the dishes", true);
        response.then().assertThat().statusCode(200).body("UserId", equalTo(expectedDataMap.get("UserId")),
                "title", equalTo(expectedDataMap.get("title")),
                "completed",equalTo(expectedDataMap.get("completed")));

        //GSON to do assertion. Homework!!!
        //2.Way: Use GSON : De-serialization: Converting Json Data to Java Object
        Map<String, Object> actualDataMap = response.as(HashMap.class);
        assertEquals(requestBodyMap.get("title"),actualDataMap.get("title"));



    }
}
