package get_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class Get06  extends HerOkuAppBaseUrl {
    /*
        Given
            https://restful-booker.herokuapp.com/booking/5
        When
            User send a GET request to the URL
        Then
            HTTP Status Code should be 200
        And
            Response content type is “application/json”
        And
            Response body should be like;
            {
                "firstname": "Mary",
                "lastname": "Jackson",
                "totalprice": 111,
                "depositpaid": false,
                "bookingdates": { "checkin": "2017-05-23",
                                  "checkout":"2019-07-02" }
            }
     */

    @Test
    public void get06() {

        //1.Step: Set the URL
        spec.pathParams("first","booking","second",5);

        //2.Step: Set the expected data

        //3.Step: Send request get response
        Response response = given().spec(spec).when().get("/{first}/{second}");
        response.prettyPrint();
        //4.Step Do the assertion
//        response.
//                then().
//                assertThat().
//                statusCode(200).
//                contentType(ContentType.JSON).
//                body("firstname",equalTo("Susan"),
//                        "lastname",equalTo("Jackson"),
//                        "totalprice",equalTo(814),
//                        "depositpaid",equalTo(false),
//                        "bookingdates.checkin",equalTo("2017-12-29"),
//                        "bookingdates.checkout",equalTo("2018-03-24"));

        //2.Way: Use JsonPath. Jsonpath is a class and it has many useful methods to navigate inside the Json Data

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        //Create JsonPath object from response object
        JsonPath json = response.jsonPath();
//        assertEquals("First name is not matching","Mark", json.getString("firstname"));
//        assertEquals("Last name is not matching","Smith", json.getString("lastname"));
//        assertEquals("Total price is not matching",560, json.getInt("totalprice"));
//        assertEquals("Deposit paid value is not matching",true, json.getBoolean("depositpaid"));
//        assertEquals("Checkin date is not matching","2016-07-20", json.getString("bookingdates.checkin"));
//        assertEquals("Checkout date is not matching","2017-08-09", json.getString("bookingdates.checkout"));

        //3.Way: Soft Assertion
        //i)Create SoftAssert Object
        SoftAssert softAssert = new SoftAssert();

        //ii)By using softAssert object do assertions
        softAssert.assertEquals(json.getString("firstname"),"Eric", "First name is not matching");
        softAssert.assertEquals(json.getString("lastname"),"Jones", "Last name is not matching");
        softAssert.assertEquals(json.getInt("totalprice"),162, "Total price is not matching");
        softAssert.assertEquals(json.getBoolean("depositpaid"),false, "Deposit paid value is not matching");
        softAssert.assertEquals(json.getString("bookingdates.checkin"),"2021-07-19", "Checkin date is not matching");
        softAssert.assertEquals(json.getString("bookingdates.checkout"),"2021-08-31", "Checkout date is not matching");

        //iii)Do not forget to use assertAll(). If you do not use assertAll() you will get green everytime but it is not meaningfull
        softAssert.assertAll();



    }
}





