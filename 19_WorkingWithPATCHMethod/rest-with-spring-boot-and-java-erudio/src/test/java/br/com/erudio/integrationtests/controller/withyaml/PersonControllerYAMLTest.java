package br.com.erudio.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.erudio.integrationtests.testcontainer.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.integrationtests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYAMLTest extends AbstractIntegrationTest {

        private static RequestSpecification specification;
        private static YMLMapper objectYmlMapper;

        private static PersonVO person;

        @BeforeAll
        public static void setup() {
                objectYmlMapper = new YMLMapper();
                // objectYmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

                person = new PersonVO();
        }

        @Test
        @Order(0)
        public void authorization() throws JsonMappingException, JsonProcessingException {
                AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

                var accessToken = given()
                                .config(RestAssuredConfig
                                                .config()
                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                .encodeContentTypeAs(
                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                ContentType.TEXT)))
                                .basePath("/auth/signin")
                                .port(TestConfigs.SERVER_PORT)
                                .contentType(TestConfigs.CONTENT_TYPE_YML)
                                .accept(TestConfigs.CONTENT_TYPE_YML)
                                .body(user, objectYmlMapper)
                                .when()
                                .post()
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .as(TokenVO.class, objectYmlMapper)
                                .getAcessToken();

                specification = new RequestSpecBuilder()
                                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                                .setBasePath("/person/v1")
                                .setPort(TestConfigs.SERVER_PORT)
                                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                                .build();
        }

        @Test
        @Order(1)
        public void testeCreate() throws JsonMappingException, JsonProcessingException {
                mockPerson();

                var createdPerson = given().spec(specification)
                                .config(RestAssuredConfig
                                                .config()
                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                .encodeContentTypeAs(
                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                ContentType.TEXT)))
                                .contentType(TestConfigs.CONTENT_TYPE_YML)
                                .accept(TestConfigs.CONTENT_TYPE_YML)
                                .body(person, objectYmlMapper)
                                .when()
                                .post()
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .as(PersonVO.class, objectYmlMapper);

                person = createdPerson;

                assertNotNull(createdPerson.getId());
                assertNotNull(createdPerson.getFirstName());

                assertTrue(createdPerson.getId() > 0);

                assertEquals("Richard", createdPerson.getFirstName());
                assertEquals("Stallman", createdPerson.getLastName());
        }

        @Test
        @Order(2)
        public void testeUpdate() throws JsonMappingException, JsonProcessingException {
                person.setLastName("Stallman Musk");
                var createdPerson = given().spec(specification)
                                .config(RestAssuredConfig
                                                .config()
                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                .encodeContentTypeAs(
                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                ContentType.TEXT)))
                                .contentType(TestConfigs.CONTENT_TYPE_YML)
                                .accept(TestConfigs.CONTENT_TYPE_YML)
                                .body(person, objectYmlMapper)
                                .when()
                                .post()
                                .then()
                                .statusCode(200)
                                .extract()
                                .body().as(PersonVO.class, objectYmlMapper);

                person = createdPerson;

                assertNotNull(createdPerson.getId());
                assertNotNull(createdPerson.getFirstName());

                assertEquals(person.getId(), createdPerson.getId());

                assertEquals("Richard", createdPerson.getFirstName());
                assertEquals("Stallman Musk", createdPerson.getLastName());
        }

        @Test
        @Order(3)
        public void testDisabled() throws JsonMappingException, JsonProcessingException {
                mockPerson();

                var persistedPerson = given().spec(specification)
                                .config(
                                                RestAssuredConfig
                                                                .config()
                                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                                .encodeContentTypeAs(
                                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                                ContentType.TEXT)))
                                .contentType(TestConfigs.CONTENT_TYPE_YML)
                                .accept(TestConfigs.CONTENT_TYPE_YML)
                                .pathParam("id", person.getId())
                                .when()
                                .patch("{id}")
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .as(PersonVO.class, objectYmlMapper);

                person = persistedPerson;

                assertNotNull(persistedPerson);

                assertNotNull(persistedPerson.getId());
                assertNotNull(persistedPerson.getFirstName());
                assertNotNull(persistedPerson.getLastName());
                assertNotNull(persistedPerson.getAddress());
                assertNotNull(persistedPerson.getGender());

                assertEquals(person.getId(), persistedPerson.getId());

                assertEquals("Richard", persistedPerson.getFirstName());
        }

        @Test
        @Order(4)
        public void testFindById() throws JsonMappingException, JsonProcessingException {
                mockPerson();

                var persistedPerson = given().spec(specification)
                                .config(
                                                RestAssuredConfig
                                                                .config()
                                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                                .encodeContentTypeAs(
                                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                                ContentType.TEXT)))
                                .contentType(TestConfigs.CONTENT_TYPE_YML)
                                .accept(TestConfigs.CONTENT_TYPE_YML)
                                .pathParam("id", person.getId())
                                .when()
                                .get("{id}")
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .as(PersonVO.class, objectYmlMapper);

                person = persistedPerson;

                assertNotNull(persistedPerson);

                assertNotNull(persistedPerson.getId());
                assertNotNull(persistedPerson.getFirstName());
                assertNotNull(persistedPerson.getLastName());
                assertNotNull(persistedPerson.getAddress());
                assertNotNull(persistedPerson.getGender());
                assertFalse(persistedPerson.getEnabled());

                assertEquals(person.getId(), persistedPerson.getId());

                assertEquals("Richard", persistedPerson.getFirstName());
        }

        @Test
        @Order(5)
        public void testeDelete() throws JsonMappingException, JsonProcessingException {
                given().spec(specification)
                                .config(RestAssuredConfig
                                                .config()
                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                .encodeContentTypeAs(
                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                ContentType.TEXT)))
                                .contentType(TestConfigs.CONTENT_TYPE_XML)
                                .accept(TestConfigs.CONTENT_TYPE_XML)
                                .pathParam("id", person.getId())
                                .when()
                                .delete("{id}")
                                .then()
                                .statusCode(204);
        }

        @Test
        @Order(6)
        public void testFindAll() throws JsonMappingException, JsonProcessingException {

                var content = given().spec(specification)
                                .config(
                                                RestAssuredConfig
                                                                .config()
                                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                                .encodeContentTypeAs(
                                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                                ContentType.TEXT)))
                                .contentType(TestConfigs.CONTENT_TYPE_YML)
                                .accept(TestConfigs.CONTENT_TYPE_YML)
                                .when()
                                .get()
                                .then()
                                .statusCode(200)
                                .extract()
                                .body()
                                .as(PersonVO[].class, objectYmlMapper);

                List<PersonVO> people = Arrays.asList(content);

                PersonVO foundPersonOne = people.get(0);

                assertNotNull(foundPersonOne.getId());
                assertNotNull(foundPersonOne.getFirstName());
                assertNotNull(foundPersonOne.getLastName());
                assertNotNull(foundPersonOne.getAddress());
                assertNotNull(foundPersonOne.getGender());

                assertEquals(3, foundPersonOne.getId());

                assertEquals("Ana Luiza", foundPersonOne.getFirstName());
                assertEquals("Oliveira Galdino", foundPersonOne.getLastName());

                PersonVO foundPersonSix = people.get(4);

                assertEquals(8, foundPersonSix.getId());

                assertEquals("Ronei", foundPersonSix.getFirstName());
                assertEquals("Antunes", foundPersonSix.getLastName());
        }

        @Test
        @Order(7)
        public void testeFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
                RequestSpecification specificationWithToken = new RequestSpecBuilder()

                                .setBasePath("/person/v1")
                                .setPort(TestConfigs.SERVER_PORT)
                                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                                .build();

                given().spec(specificationWithToken)
                                .config(RestAssuredConfig
                                                .config()
                                                .encoderConfig(EncoderConfig.encoderConfig()
                                                                .encodeContentTypeAs(
                                                                                TestConfigs.CONTENT_TYPE_YML,
                                                                                ContentType.TEXT)))
                                .contentType(TestConfigs.CONTENT_TYPE_XML)
                                .accept(TestConfigs.CONTENT_TYPE_XML)
                                .when()
                                .get()
                                .then()
                                .statusCode(403);
        }

        private void mockPerson() {
                person.setId(1L);
                person.setFirstName("Richard");
                person.setLastName("Stallman");
                person.setAddress("New York - USA");
                person.setGender("Male");
                person.setEnabled(true);
        }

}
