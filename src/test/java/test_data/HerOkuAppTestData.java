package test_data;

import java.util.HashMap;

public class HerOkuAppTestData {



    public static String expectedTestData(String firstname, String lastname, Integer totalprice, Boolean depositpaid, String checkin, String checkout) {
        String expectedData = "{"+"\"firstname\":"+firstname+","+
                "\"lastname\":"+lastname+","+
                "\"totalprice\":"+ totalprice+","+
                "\"depositpaid\":"+ depositpaid+","+
                "\"bookingdates\":"+"{" +
            "\"checkin\":" + checkin+ ","+
                    "\"checkout\":"+ checkout+
                 "}"+"}";




        return expectedData;

    }
}
