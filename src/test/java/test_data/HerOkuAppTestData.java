package test_data;

import java.util.HashMap;

public class HerOkuAppTestData {



    public static String expectedTestData(String firstname, String lastname, Integer totalprice, Boolean depositpaid, String checkin, String checkout) {
        //{"firstname":Susan,"lastname":Smith,"totalprice":408,"depositpaid":false,"bookingdates":{"checkin":2016-03-11,"checkout":2020-05-11}}
        String expectedData = "{"+"\"firstname\":"+"\""+firstname+"\","+
                "\"lastname\":"+"\""+lastname+"\","+
                "\"totalprice\":"+ totalprice+","+
                "\"depositpaid\":"+ depositpaid+","+
                "\"bookingdates\":"+"{" +
            "\"checkin\":" + "\""+checkin+ "\","+
                    "\"checkout\":"+"\""+ checkout+
                 "\"}"+"}";




        return expectedData;

    }
}
