package stepdefinitions;

import dbutil.DBUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Steps {

    private static final String BASE_URL = "http://localhost:8080/sushmatm/Student/1.0.0/";

    RequestSpecification request;
    private Response response;
    DBUtil dbUtil = new DBUtil();
    static int stdId;

    public Steps() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
    }


    @When("new student with {int} , {string} ,{string} {string} ,{string}")
    public void new_student_with(int studentID, String firstName, String lastName, String className, String nationality) {
        String jsonBody = "{\"studentID\":0,\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\",\"className\":\"" + className + "\",\"nationality\":\"" + nationality + "\"}";
        System.out.println("\n\n" + jsonBody);
        request.header("Content-Type", "application/json");
        request.body(jsonBody);
        response = request.post("/student");
        JsonPath path = response.jsonPath();
        stdId = path.get("studentID");
        System.out.println("student ID is " + stdId);
    }

    @Then("student should be added and return a success {int}")
    public void student_should_be_added_and_return_a_success(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @When("For given studentID className to be updated is {string} , with exisitng values {string} {string} {string}")
    public void for_given_studentID_className_to_be_updated_is_with_exisitng_values(String firstName, String lastName, String className, String nationality) {
        System.out.println("student ID update is " + stdId);
        response = request.get("student/" + stdId);
        String jsonBody = "{\"studentID\":\""+stdId+"\",\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\",\"className\":\"" + className + "\",\"nationality\":\"" + nationality + "\"}";
        request.header("Content-Type", "application/json");
        request.body(jsonBody);
        response = request.put("/student");
    }

    @Then("student data should be updated and return a success status {int}")
    public void student_data_should_be_updated_and_return_a_success_status(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @When("Student ID  is {int}")
    public void student_ID_is(int studentID) {
        response = request.get("student/" + studentID);
    }

    @Then("get all the student info for {int}")
    public void get_all_the_student_info(int studentId) throws SQLException {
        Assert.assertEquals(200, response.getStatusCode());
        ResultSet resultSet = dbUtil.executeSelectQuery("select * from Student where studentId = " + studentId + "");
        while (resultSet.next()) {
            String firstNameResp = getJsonPath(response, "firstName");
            String firstNameDB = resultSet.getString(2);
            Assert.assertEquals(firstNameDB, firstNameResp);
        }
    }

    @Then("Status_code equals {int}")
    public void statuscode_equals_(int arg) {
        Assert.assertEquals(arg, response.getStatusCode());
    }


    @When("Student ClassName  is {string}")
    public void student_ID_is(String className) {
        response = request.get("/student?className=" + className);
    }

    @Then("get all the student info for {string}")
    public void get_all_the_student_info_for(String className) throws SQLException {
        Assert.assertEquals(200, response.getStatusCode());
        ResultSet resultSet = dbUtil.executeSelectQuery("select * from Student where studentId = '" + className + "'");
        while (resultSet.next()) {
            String firstNameResp = getJsonPath(response, "className");
            String firstNameDB = resultSet.getString(4);
            Assert.assertEquals(firstNameDB, firstNameResp);
        }
    }

    @When("Student to delete is {int}")
    public void student_to_delete_is(Integer studentID) {
        response = request.get("/student?className=" + stdId);
    }

    @Then("delete successful and return a success {int}")
    public void delete_successful_and_return_a_success(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    public String getJsonPath(Response response, String key) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }

}
