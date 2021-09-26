package post_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.HerOkuAppPostResponseBodyPojo;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class PostWithPojo02 extends HerOkuAppBaseUrl {

    /*
        Given
            https://restful-booker.herokuapp.com/booking

            {
                "firstname": "Suleyman",
                "lastname": "Alptekin",
                "totalprice": 999,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2021-09-21",
                    "checkout": "2021-12-21"
                 }
                 "additionalneeds": "Breakfast with white tea"
             }
        When
 		    I send GET Request to the URL
 	    Then
 		    Status code is 200
 		And
 		    Response body is like
                                   {
                                    "firstname": "Mary",
                                    "lastname": "Smith",
                                    "totalprice": 647,
                                    "depositpaid": false,
                                    "bookingdates": {
                                        "checkin": "2016-02-05",
                                        "checkout": "2021-01-16"
                                     }
                                     "additionalneeds": "Breakfast"
                                  }
     */

    @Test
    public void postWithPojo02() {
        //1.Step: Set the url
        spec.pathParam("first","booking");

        //2.Step: Set request body
        BookingDatesPojo bookingDates = new BookingDatesPojo("2021-09-21","2021-12-21");
        BookingPojo requestBody = new BookingPojo("Suleyman","Alptekin",999,true,bookingDates,"Breakfast with white tea");

        //3.Step: Send the request and get the response
        Response response = given().spec(spec).contentType(ContentType.JSON).body(requestBody).when().post("/{first}");
        response.prettyPrint();

        HerOkuAppPostResponseBodyPojo actualData = response.as(HerOkuAppPostResponseBodyPojo.class);
        System.out.println(actualData);

        //4.Step: Do assertion
        assertEquals(200,response.getStatusCode());
        assertEquals(requestBody.getFirstname(),actualData.getBooking().getFirstname());
        assertEquals(requestBody.getLastname(),actualData.getBooking().getLastname());
        assertEquals(requestBody.getTotalprice(),actualData.getBooking().getTotalprice());
        assertEquals(requestBody.getDepositpaid(),actualData.getBooking().getDepositpaid());
        assertEquals(requestBody.getBookingdates().getCheckin(),actualData.getBooking().getBookingdates().getCheckin());
        assertEquals(requestBody.getBookingdates().getCheckout(),actualData.getBooking().getBookingdates().getCheckout());
        assertEquals(requestBody.getAdditionalneeds(),actualData.getBooking().getAdditionalneeds());
    }

    @Test
    public void postWithPojo03() {
        //1.Step: Set the url
        spec.pathParam("first","booking");

        //2.Step: Set request body
        BookingDatesPojo bookingDates = new BookingDatesPojo("2021-09-21","2021-12-21");
        BookingPojo requestBody = new BookingPojo("Suleyman","Alptekin",999,true,bookingDates,"Breakfast with white tea");

        //3.Step: Send the request and get the response
        Response response = given().spec(spec).contentType(ContentType.JSON).body(requestBody).when().post("/{first}");
        response.prettyPrint();

        JsonPath json = response.jsonPath();

        Integer bookingId = json.getInt("bookingid");
        System.out.println(bookingId);

        //Set the url for GET Request
        spec.pathParams("first","booking","second",bookingId);

        //Send the GET Request
        Response response2 = given().spec(spec).when().get("/{first}/{second}");
        response2.prettyPrint();

        //Do the assertion
        BookingPojo actualData = response2.as(BookingPojo.class);

        assertEquals(requestBody.getFirstname(),actualData.getFirstname());
        assertEquals(requestBody.getLastname(),actualData.getLastname());
        assertEquals(requestBody.getTotalprice(),actualData.getTotalprice());
        assertEquals(requestBody.getDepositpaid(),actualData.getDepositpaid());
        assertEquals(requestBody.getBookingdates().getCheckin(),actualData.getBookingdates().getCheckin());
        assertEquals(requestBody.getBookingdates().getCheckout(),actualData.getBookingdates().getCheckout());
        assertEquals(requestBody.getAdditionalneeds(),actualData.getAdditionalneeds());


    }



}
