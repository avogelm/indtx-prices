package com.avogelm.indtxprices;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndtxPricesApplicationE2ETest {

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test()
    @DisplayName("Test 1: 10:00 del día 14 - producto 35455 - brand 1")
    void test1() {

        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        String queryTS = LocalDateTime.of(2020, 6, 14, 10, 0, 0)
                .format(df);

        when()
                .get("api/prices/1/35455?timestamp="+queryTS)
                .then()
                .statusCode(200)
                .body("priceList", equalTo(1))
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("price", equalTo(35.50f))
                .body("startDate", equalTo(expectedStartDate.format(df)))
                .body("endDate", equalTo(expectedEndDate.format(df)));
    }

    @Test()
    @DisplayName("Test 2: 16:00 del día 14 - producto 35455 - brand 1")
    void test2() {

        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 6, 14, 18, 30, 0);

        String queryTS = LocalDateTime.of(2020, 6, 14, 16, 0, 0)
                .format(df);

        when()
                .get("api/prices/1/35455?timestamp="+queryTS)
                .then()
                .statusCode(200)
                .body("priceList", equalTo(2))
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("price", equalTo(25.45f))
                .body("startDate", equalTo(expectedStartDate.format(df)))
                .body("endDate", equalTo(expectedEndDate.format(df)));
    }

    @Test()
    @DisplayName("Test 3: 21:00 del día 14 - producto 35455 - brand 1")
    void test3() {

        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        String queryTS = LocalDateTime.of(2020, 6, 14, 21, 0, 0)
                .format(df);

        when()
                .get("api/prices/1/35455?timestamp="+queryTS)
                .then()
                .statusCode(200)
                .body("priceList", equalTo(1))
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("price", equalTo(35.50f))
                .body("startDate", equalTo(expectedStartDate.format(df)))
                .body("endDate", equalTo(expectedEndDate.format(df)));
    }

    @Test()
    @DisplayName("Test 4: 10:00 del día 15 - producto 35455 - brand 1")
    void test4() {

        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 15, 0, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 6, 15, 11, 0, 0);

        String queryTS = LocalDateTime.of(2020, 6, 15, 10, 0, 0)
                .format(df);

        when()
                .get("api/prices/1/35455?timestamp="+queryTS)
                .then()
                .statusCode(200)
                .body("priceList", equalTo(3))
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("price", equalTo(30.50f))
                .body("startDate", equalTo(expectedStartDate.format(df)))
                .body("endDate", equalTo(expectedEndDate.format(df)));
    }

    @Test()
    @DisplayName("Test 5: 21:00 del día 16 - producto 35455 - brand 1")
    void test5() {

        LocalDateTime expectedStartDate = LocalDateTime.of(2020, 6, 15, 16, 0, 0);
        LocalDateTime expectedEndDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        String queryTS = LocalDateTime.of(2020, 6, 16, 21, 0, 0)
                .format(df);

        when()
                .get("api/prices/1/35455?timestamp="+queryTS)
                .then()
                .statusCode(200)
                .body("priceList", equalTo(4))
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("price", equalTo(38.95f))
                .body("startDate", equalTo(expectedStartDate.format(df)))
                .body("endDate", equalTo(expectedEndDate.format(df)));
    }

    @Test()
    @DisplayName("Test de Error 404: 21:00 del día 16 - producto 1 - brand 4")
    void throwsNotFoundError() {
        String queryTS = LocalDateTime.of(2020, 6, 16, 21, 0, 0)
                .format(df);
        when()
                .get("api/prices/4/1?timestamp="+queryTS)
                .then()
                .statusCode(404);
    }
}