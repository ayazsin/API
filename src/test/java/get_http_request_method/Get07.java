package get_http_request_method;

import base_urls.JsonPlaceHolderBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Get07 extends JsonPlaceHolderBaseUrl {
    /*
        Given
        https://jsonplaceholder.typicode.com/todos
        When
             I send GET Request to URL
         Then
             1)Status code is 200
             2)Print all ids greater than 190 on the console
               Assert that there are 10 ids greater than 190
             3)Print all userIds less than 5 on the console
               Assert that maximum userId less than 5 is 4
             4)Print all titles whose ids are less than 5
               Assert that "delectus aut autem" is one of the titles whose id is less than 5
     */

      @Test
        public void get07() {

          //1.Step: Set the URL
          spec.pathParam("first","todos");

          //2.Step: Set the expected data

          //3.Step: Send request get response

          Response response = given().spec(spec).when().get("/{first}");
          response.prettyPrint();

          //4.Step Do the assertion
          response.then().assertThat().statusCode(200);

          //Print all ids greater than 190 on the console
          JsonPath json = response.jsonPath();

          List<Integer> idList = json.getList("findAll{it.id>190}.id");//Groovy Language //it ->From the Json Data which we are working in
          System.out.println(idList);//[191, 192, ... 200]

          //Assert that there are 10 ids greater than 190
          assertEquals("Id list does not have expected size",10, idList.size());
          //Ass

          //Print all userIds less than 5 on the console
          List<Integer> userIdList = json.getList("findAll{it.userId<5}.userId");
          System.out.println(userIdList);
          //Assert that maximum userId less than 5 is 4
          Collections.sort(userIdList);
          assertEquals((Integer) 4,userIdList.get(userIdList.size()-1));

          //Print all titles whose ids are less than 5
          List<String > titleList = json.getList("findAll{it.id<5}.title");
          System.out.println(titleList);
          //Assert that "delectus aut autem" is one of the titles whose id is less than 5
          //1.Way
          assertTrue(titleList.contains("delectus aut autem"));
          //2.Way
          assertTrue(titleList.stream().anyMatch(t->t.equals("delectus aut autem")));



      }


}
