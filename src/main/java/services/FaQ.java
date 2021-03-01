package services;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.TestBase;
import utils.TestUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import static io.restassured.RestAssured.given;

public class FaQ extends TestBase {

    @Parameters({ "dataEnv" })
    public static void mdmFaq(String testCase, String dataEnv,String code, String descript, int statusCode,
                                           String description) throws IOException, ParseException {

        RestAssured.baseURI = baseUrl;

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/main/resources/" + dataEnv + "/faq.json"));

        String endpoint = (String) config.get("resource-url");
 //       String authorization = getToken(dataEnv);
        JsonObject requestBody = TestUtils.generateJson(dataEnv, "faq.json", testCase);
        String body = requestBody.toString();

        TestUtils.testTitle("<b>Description</b>");
        testInfo.get().info(description);

        TestUtils.testTitle("<b>ENDPOINT</b>");
        testInfo.get().info(baseUrl + endpoint);

        TestUtils.testTitle("<b>Request Body</b>");
        testInfo.get().info(MarkupHelper.createCodeBlock(body));

        Response res = given().header("Content-type", "application/json")
                .body(requestBody).

                        when().post(endpoint).then().assertThat().extract().response();

        TestUtils.testTitle("Response Body");
        testInfo.get().info(MarkupHelper.createCodeBlock(res.prettyPrint()));

        int statCode = res.getStatusCode();
        TestUtils.testTitle("Status Code");
        testInfo.get().info(Integer.toString(statCode));

        Assert.assertEquals(statCode, statusCode);

        String response = res.asString();
        JsonPath jsonRes = new JsonPath(response);
        String description1 = jsonRes.getString("description");
        String code1 = jsonRes.getString("code");

        Assert.assertEquals(description1, descript);
        Assert.assertEquals(code1, code);

    }
    @Parameters({ "dataEnv" })
    @Test(groups = { "Regression" })
    public void validFaq(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        mdmFaq("validFaqBody", dataEnv, "0", "Success", 200, "get list of FAQ");
    }

    @Parameters({ "dataEnv" })
    public static void inValidpayload(String testCase, String dataEnv,String message, String status, int statusCode,
                              String description) throws IOException, ParseException {

        RestAssured.baseURI = baseUrl;

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/main/resources/" + dataEnv + "/faq.json"));

        String endpoint = (String) config.get("resource-url");
        //       String authorization = getToken(dataEnv);
        JsonObject requestBody = TestUtils.generateJson(dataEnv, "faq.json", testCase);
        String body = requestBody.toString();

        TestUtils.testTitle("<b>Description</b>");
        testInfo.get().info(description);

        TestUtils.testTitle("<b>ENDPOINT</b>");
        testInfo.get().info(baseUrl + endpoint);

        TestUtils.testTitle("<b>Request Body</b>");
        testInfo.get().info(MarkupHelper.createCodeBlock(body));

        Response res = given().header("Content-type", "application/json")
                .body(requestBody).

                        when().post(endpoint).then().assertThat().extract().response();

        TestUtils.testTitle("Response Body");
        testInfo.get().info(MarkupHelper.createCodeBlock(res.prettyPrint()));

        int statCode = res.getStatusCode();
        TestUtils.testTitle("Status Code");
        testInfo.get().info(Integer.toString(statCode));

        Assert.assertEquals(statCode, statusCode);

        String response = res.asString();
        JsonPath jsonRes = new JsonPath(response);
        String description1 = jsonRes.getString("message");
        String code1 = jsonRes.getString("status");

        Assert.assertEquals(description1, message);
        Assert.assertEquals(code1, status);

    }

    @Parameters({ "dataEnv" })
    @Test(groups = { "Regression" })
    public void emptyStartIndex(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        inValidpayload("invalidStartIndex", dataEnv, "StartIndex is required", "ValidationException", 400,"Empty startIndex and valid size");
    }

    @Parameters({ "dataEnv" })
    @Test(groups = { "Regression" })
    public void emptysize(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        inValidpayload("invalidEmptySize", dataEnv, "Size is required", "ValidationException", 400,"valid startIndex and Empty size");
    }
    @Parameters({ "dataEnv" })
    @Test(groups = { "Regression" })
    public void emptystartIndexAndSize(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        inValidpayload("EmptySizeAndIndex", dataEnv, "Size is required", "ValidationException", 400,"Empty startIndex and Empty size");
    }

}
