package post_http_request_method;

import Utils.JsonUtil;
import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.HerOkuAppPostResponseBodyPojo;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class PostGetWithObjectMapperAndPojo01 extends HerOkuAppBaseUrl {

    /*
        Given
             https://restful-booker.herokuapp.com/booking

             {
                  "firstname": "Selim",
                  "lastname": "Ak",
                  "totalprice": 11111,
                  "depositpaid": true,
                  "bookingdates": {
                                    "checkin": "2020-09-09",
                                    "checkout": "2020-09-21"
                                   }
             }
        When
            I send POST Request to the Url
        And
            I send GET Request to the Url
        Then
            Status code is 200
        And
            GET response body should be like {
                                                    "firstname": "Selim",
                                                    "lastname": "Ak",
                                                    "totalprice": 11111,
                                                    "depositpaid": true,
                                                    "bookingdates": {
                                                        "checkin": "2020-09-09",
                                                        "checkout": "2020-09-21"
                                                    }
                                                },
                                                  "addtionalneeds": "Breakfast"
                                             }
     */

    @Test
    public void postGetWithObjectMapperAndPojo01() {

                //TO SEND POST REQUEST WE DID 1.STEP, 2.STEP AND 3.STEP
        //1.Step: Set the URL
        spec.pathParam("first","booking");

        //2.Step: Set the expected data
        BookingDatesPojo bookingDates = new BookingDatesPojo("2020-09-09","2020-09-21");
        BookingPojo requestBody = new BookingPojo("Selim","Ak",11111,true,bookingDates,"Breakfast");

        System.out.println(requestBody);

        //3.Step: Send the post request and get the response
        Response response = given().spec(spec).contentType(ContentType.JSON).body(requestBody).when().post("/{first}");
        response.prettyPrint();

                    //CONVERT POST RESPONSE BODY TO JAVA OBJECT BY USING OBJECT MAPPER

        HerOkuAppPostResponseBodyPojo postResponseBodyInPojo = JsonUtil.convertJsonToJava(response.asString(), HerOkuAppPostResponseBodyPojo.class);
        System.out.println(postResponseBodyInPojo);
                    //FROM postResponseBodyInPojo WE GOT THE VALUE OF bookingId by using GETTER OF bookingId

        Integer bookingId = postResponseBodyInPojo.getBookingid();
                    //BY USING bookinId I WILL SEND GET REQUEST IN 1.STEP, 2.STEP AND 3.STEP
        //1.Step: Set the URL for GET request
        spec.pathParams("first","booking","second", bookingId);

        //2.Step: Set the expected data
        //No need to create data because the data which us sent un the POST Request will be expected data

        //3.Step: Send the get request and get the response
        Response response2 = given().spec(spec).when().get("/{first}/{second}");
        response2.prettyPrint();
                    //I CONVERT GET RESPONSE BODY TO JAVA OBJECT BY USING OBJECT MAPPER
        BookingPojo getResponseBodyInPojo = JsonUtil.convertJsonToJava(response2.asString(),BookingPojo.class);
                    //BY USING requestBody and getResponseBodyInPojo, I DID ASSERTIONS
        //4.Step: Do assertion
        assertEquals(200, response2.getStatusCode());
        assertEquals(requestBody.getFirstname(),getResponseBodyInPojo.getFirstname());
        assertEquals(requestBody.getLastname(),getResponseBodyInPojo.getLastname());
        assertEquals(requestBody.getTotalprice(),getResponseBodyInPojo.getTotalprice());
        assertEquals(requestBody.getDepositpaid(),getResponseBodyInPojo.getDepositpaid());
        assertEquals(requestBody.getBookingdates().getCheckin(),getResponseBodyInPojo.getBookingdates().getCheckin());
        assertEquals(requestBody.getBookingdates().getCheckout(),getResponseBodyInPojo.getBookingdates().getCheckout());
        assertEquals(requestBody.getAdditionalneeds(),getResponseBodyInPojo.getAdditionalneeds());
    }
}
