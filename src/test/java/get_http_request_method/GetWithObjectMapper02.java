package get_http_request_method;

import Utils.JsonUtil;
import base_urls.HerOkuAppBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;
import test_data.HerOkuAppTestData;
import test_data.JsonPlaceHolderTestData;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class GetWithObjectMapper02 extends HerOkuAppBaseUrl {

    /*

        Given
                 https://restful-booker.herokuapp.com/booking/2
        When
                I send GET Request to the URL
        Then
                Status code is 200
                And response body is like {
                                            "firstname": "Mark",
                                            "lastname": "Ericsson",
                                            "totalprice": 726,
                                            "depositpaid": true,
                                            "bookingdates": {
                                                "checkin": "2015-08-07",
                                                "checkout": "2020-10-25"
                                             }
                                          }
     */

    @Test
    public void getWithObjectMapper02(){
        //1.Step: Set the url
        spec.pathParams("first","booking","second",2);

        //2.Step: Set the expected data
        //1.Way
//        String expectedData = "{\n" +
//                "\"firstname\": \"Mary\",\n" +
//                "\"lastname\": \"Smith\",\n" +
//                "\"totalprice\": 638,\n" +
//                "\"depositpaid\": false,\n" +
//                "\"bookingdates\": {\n" +
//                                    "\"checkin\": \"2017-04-20\",\n" +
//                                    "\"checkout\": \"2021-02-04\"\n" +
//                                    "},\n" +
//                "\"additionalneeds\": \"Breakfast\"\n" +
//                "}";
//
//        HashMap<String, Object> expectedDataMap = JsonUtil.convertJsonToJava(expectedData, HashMap.class);

        //2.Way: Create a setUp method to convert Json data to String dynamically ==>Homework

        HerOkuAppTestData expected = new HerOkuAppTestData();
        String expectedData = expected.expectedTestData("Susan","Smith", 408,false,"2016-03-11","2020-05-11");
        HashMap<String, Object> expectedDataMap = JsonUtil.convertJsonToJava(expectedData,HashMap.class);

        //3.Step: Send the request and get the response
        Response response = given().spec(spec).when().get("/{first}/{second}");
        response.prettyPrint();

        HashMap<String,Object> actualDataMap = JsonUtil.convertJsonToJava(response.asString(),HashMap.class);

        //4.Step: Do assertions
        assertEquals(200, response.getStatusCode());

        assertEquals(expectedDataMap.get("firstname"), actualDataMap.get("firstname"));
        assertEquals(expectedDataMap.get("lastname"), actualDataMap.get("lastname"));
        assertEquals(expectedDataMap.get("totalprice"), actualDataMap.get("totalprice"));
        assertEquals(expectedDataMap.get("depositpaid"), actualDataMap.get("depositpaid"));
        assertEquals(((Map)expectedDataMap.get("bookingdates")).get("checkin"), ((Map)actualDataMap.get("bookingdates")).get("checkin"));
        assertEquals(((Map)expectedDataMap.get("bookingdates")).get("checkout"), ((Map)actualDataMap.get("bookingdates")).get("checkout"));


    }



}