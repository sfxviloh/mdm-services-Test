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


public class FeedbackSurvey extends TestBase {
    @Parameters({ "dataEnv" })
    public static void feebackSurvey(String testCase, String dataEnv,String code, String descript, int statusCode,
                              String description) throws IOException, ParseException {

        RestAssured.baseURI = baseUrl;

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/main/resources/" + dataEnv + "/feedbackSurvey.json"));

        String endpoint = (String) config.get("resource-url");
        JsonObject requestBody = TestUtils.generateJson(dataEnv, "feedbackSurvey.json", testCase);
        String body = requestBody.toString();

        TestUtils.testTitle("<b>Description</b>");
        testInfo.get().info(description);

        TestUtils.testTitle("<b>ENDPOINT</b>");
        testInfo.get().info(baseUrl + endpoint);

        TestUtils.testTitle("<b>Request Body</b>");
        testInfo.get().info(MarkupHelper.createCodeBlock(body));

        Response res = given().header("Content-type", "application/json").header("User-UUID","ad275d41-b90e-4691-984d-b90313b2ba27")
                .header("char-enc","CIPHER").header("User-Agent","Biocapture Smart Client, 1.0, Release Date: 2020-10-30")
                .header("Connection","keep-alive").header("Client-ID","smartclient").header("Device-Type","WIN")
                .header("Accept-Encoding","gzip, deflate, br").header("Mac-Address","KuajsV1ZsQ7pVHfULLOygQ==").header("Os-Version","10.0.19042")
                .header("sc-auth-key","FH2TDIHFjhRYskRXO4sqog5yYE9UQyd5JzmBkzIiKoFXV5Nd8OlqR9zoe25vT8QvhPKAmf1nCWls1CfSXNSHv36KmBbbFuYTNvDdEyz+XkfCHVLrX/R2TuZBIPXLBU/T")
                .header("Device-ID","FH2TDIHFjhRYskRXO4sqog5yYE9UQyd5JzmBkzIiKoGNM28ENm3pkJ4Hymy+QxbSIsCKTv+QeK6W2pkxUo5cExWJpZoX1TzsvViFHzIaOJw=")
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
    public static void inValidpayload(String testCase, String dataEnv,String message, String status, int statusCode,
                                      String description) throws IOException, ParseException {

        RestAssured.baseURI = baseUrl;

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/main/resources/" + dataEnv + "/feedbackSurvey.json"));

        String endpoint = (String) config.get("resource-url");
        //       String authorization = getToken(dataEnv);
        JsonObject requestBody = TestUtils.generateJson(dataEnv, "feedbackSurvey.json", testCase);
        String body = requestBody.toString();

        TestUtils.testTitle("<b>Description</b>");
        testInfo.get().info(description);

        TestUtils.testTitle("<b>ENDPOINT</b>");
        testInfo.get().info(baseUrl + endpoint);

        TestUtils.testTitle("<b>Request Body</b>");
        testInfo.get().info(MarkupHelper.createCodeBlock(body));

        Response res = given().header("Content-type", "application/json").header("User-UUID","ad275d41-b90e-4691-984d-b90313b2ba27")
                .header("char-enc","CIPHER").header("User-Agent","Biocapture Smart Client, 1.0, Release Date: 2020-10-30")
                .header("Connection","keep-alive").header("Client-ID","smartclient").header("Device-Type","WIN")
                .header("Accept-Encoding","gzip, deflate, br").header("Mac-Address","KuajsV1ZsQ7pVHfULLOygQ==").header("Os-Version","10.0.19042")
                .header("sc-auth-key","FH2TDIHFjhRYskRXO4sqog5yYE9UQyd5JzmBkzIiKoFXV5Nd8OlqR9zoe25vT8QvhPKAmf1nCWls1CfSXNSHv36KmBbbFuYTNvDdEyz+XkfCHVLrX/R2TuZBIPXLBU/T")
                .header("Device-ID","FH2TDIHFjhRYskRXO4sqog5yYE9UQyd5JzmBkzIiKoGNM28ENm3pkJ4Hymy+QxbSIsCKTv+QeK6W2pkxUo5cExWJpZoX1TzsvViFHzIaOJw=")
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
    public void validFeedBack(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        feebackSurvey("validFeedback", dataEnv, "0", "Success", 200, "Retrieve survey questions with a valid email provided");
    }

    @Parameters({ "dataEnv" })
    @Test(groups = { "Regression" })
    public void emptyEmailField(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        inValidpayload("emptyEmail", dataEnv, "user email must be provided", "ValidationException", 400,"Empty Email Field provided");
    }

    @Parameters({ "dataEnv" })
    public static void submitSurveyResponse(String testCase, String dataEnv,String code, String descript, int statusCode,
                                     String description) throws IOException, ParseException {

        RestAssured.baseURI = baseUrl;

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser
                .parse(new FileReader("src/main/resources/" + dataEnv + "/feedbackSurvey.json"));

        String endpoint = (String) config.get("resource-url2");
        JsonObject requestBody = TestUtils.generateJson(dataEnv, "feedbackSurvey.json", testCase);
        String body = requestBody.toString();

        TestUtils.testTitle("<b>Description</b>");
        testInfo.get().info(description);

        TestUtils.testTitle("<b>ENDPOINT</b>");
        testInfo.get().info(baseUrl + endpoint);

        TestUtils.testTitle("<b>Request Body</b>");
        testInfo.get().info(MarkupHelper.createCodeBlock(body));

        Response res = given().header("Content-type", "application/json").header("User-UUID","ad275d41-b90e-4691-984d-b90313b2ba27")
                .header("char-enc","CIPHER").header("User-Agent","Biocapture Smart Client, 1.0, Release Date: 2020-10-30")
                .header("Connection","keep-alive").header("Client-ID","smartclient").header("Device-Type","WIN")
                .header("Accept-Encoding","gzip, deflate, br").header("Mac-Address","KuajsV1ZsQ7pVHfULLOygQ==").header("Os-Version","10.0.19042")
                .header("sc-auth-key","FH2TDIHFjhRYskRXO4sqog5yYE9UQyd5JzmBkzIiKoFXV5Nd8OlqR9zoe25vT8QvhPKAmf1nCWls1CfSXNSHv36KmBbbFuYTNvDdEyz+XkfCHVLrX/R2TuZBIPXLBU/T")
                .header("Device-ID","FH2TDIHFjhRYskRXO4sqog5yYE9UQyd5JzmBkzIiKoGNM28ENm3pkJ4Hymy+QxbSIsCKTv+QeK6W2pkxUo5cExWJpZoX1TzsvViFHzIaOJw=")
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
    public void validSurveyResponse(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        submitSurveyResponse("validSurveyResponse", dataEnv, "0", "Success", 200, "Submit survey response with a valid email provided");
    }
    @Parameters({ "dataEnv" })
    @Test(groups = { "Regression" })
    public void emptyEmailFieldSurveyResponse(String dataEnv) throws FileNotFoundException, IOException, ParseException {
        inValidpayload("emptyEmailSurveyResponse", dataEnv, "user email must be provided", "ValidationException", 400,"Submit response with Empty Email Field provided");
    }


}
