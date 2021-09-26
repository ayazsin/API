package get_http_request_method;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.response.Response;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class GetWithPojo01 extends HerOkuAppBaseUrl {
    /*
        Given
            https://restful-booker.herokuapp.com/booking/2
        When
 		    I send GET Request to the URL
 	    Then
 		    Status code is 200
 		And
 		    Response body is like {
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
    public void getWithPojo01() {

        spec.pathParams("first","booking","second",2);

        BookingDatesPojo bookingDates = new BookingDatesPojo("2016-02-05","2021-01-16");
        BookingPojo expectedData = new BookingPojo("Mary","Smith",647,false,bookingDates,"Breakfast");
        Response response = given().spec(spec).when().get("/{first}/{second}");
        BookingPojo actualData = response.as(BookingPojo.class);
        System.out.println(actualData);
        assertEquals(200,response.getStatusCode());
        assertEquals("First names are not matching" + expectedData.getFirstname(),actualData.getFirstname());
        assertEquals("Last names are not matching" +expectedData.getLastname(),actualData.getLastname());
        assertEquals("Total Prices are not matching" +expectedData.getTotalprice(),actualData.getTotalprice());
        assertEquals("Deposit paid are not matching" +expectedData.getDepositpaid(),actualData.getDepositpaid());
        assertEquals("Check in dates are not matching" +expectedData.getBookingdates().getCheckin(),actualData.getBookingdates().getCheckin());
        assertEquals("Check out dates are not matching" +expectedData.getBookingdates().getCheckout(),actualData.getBookingdates().getCheckout());

    }

}
