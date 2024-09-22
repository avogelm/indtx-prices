package com.avogelm.indtxprices.drivenadapters;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:indtxprices"
})
class PricesH2RepositoryTest {

    @Autowired
    PricesH2Repository pricesH2Repository;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");

    float price1 = 35.5f,
            price2 = 25.45f;

    @BeforeEach
    public void setUp() throws Exception {

        pricesH2Repository.deleteAll();

        PriceList testPriceList = new PriceList();
        System.out.println(testPriceList.getPriceList());
        testPriceList.setBrandId(1);
        testPriceList.setProductId(35455);
        testPriceList.setCurrency("EUR");

        testPriceList.setPriority(0);
        testPriceList.setPrice(price1);
        testPriceList.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        testPriceList.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        pricesH2Repository.saveAndFlush(testPriceList);

        testPriceList.setPriority(1);
        testPriceList.setPrice(price2);
        testPriceList.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0));
        testPriceList.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0));

        pricesH2Repository.saveAndFlush(testPriceList);

    }

    @AfterEach
    public void tearDown() {
        pricesH2Repository.deleteAll();
    }

    @Test
    public void notFound() {
        PriceList result = this.pricesH2Repository.getProductPrice(
                1,
                35455,
                LocalDateTime.now()
        );

        assertNull(result);
    }

    @Test
    public void foundReturnsHigherPriorityPriceList() throws Exception {
        PriceList result = this.pricesH2Repository.getProductPrice(
                1,
                35455,
                LocalDateTime.of(2020, 6, 14, 17, 0, 0)
        );

        assertNotNull(result);
        assertEquals(price2, result.getPrice());
    }
}