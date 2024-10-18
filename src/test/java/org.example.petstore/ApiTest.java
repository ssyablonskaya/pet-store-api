package org.example.petstore;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.petstore.enums.PetCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Pet createdPet;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test(priority = 1)
    public void createPetTest() {
        Pet newPet = Pet.builder()
                .name(RandomStringUtils.randomAlphabetic(7))
                .category(new Category(PetCategory.DOGS.getId(), PetCategory.DOGS.getName()))
                .photoUrls(new String[]{"https://example.com/photo1", "https://example.com/photo2"})
                .tags(new Tag[]{new Tag(1, "tag1")})
                .status("available")
                .build();

        long petId = Long.parseLong(
                given()
                        .contentType(ContentType.JSON)
                        .body(newPet)
                        .log().all()
                        .when()
                        .post("/pet")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .extract().jsonPath().getString("id"));

        LOGGER.info("Created pet ID: {}", petId);

        createdPet =
                given()
                        .pathParam("petId", petId)
                        .log().all()
                        .when()
                        .get("/pet/{petId}")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .extract().jsonPath().getObject("", Pet.class);
    }

    @Test(priority = 2, dependsOnMethods = "createPetTest")
    public void updatePetStatusTest() {
        String newStatus = "sold";
        createdPet.setStatus(newStatus);
        given()
                .contentType(ContentType.JSON)
                .body(createdPet)
                .log().all()
                .when()
                .put("/pet")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo(newStatus));
    }

}

