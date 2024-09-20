package com.avogelm.indtxprices.application.core.handlers;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;
import com.avogelm.indtxprices.application.drivenports.PricesRepository;
import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.ProductPriceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class GetProductPriceHandlerTest {

    @MockBean
    PricesRepository pricesRepository;

    @Autowired
    private GetProductPriceUseCase getProductPriceUseCase;

    @Test
    void errorInRepository() {
        int brandId = 1;
        int productId = 35455;
        Date timestamp = new Date();

        doThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(this.pricesRepository)
                .getProductPrices(anyInt(), anyInt(), any(Date.class));

        try {
            this.getProductPriceUseCase.handle(
                    brandId,
                    productId,
                    timestamp
            );
        } catch (Exception e) {
            assertInstanceOf(ResponseStatusException.class, e);
        }

        verify(this.pricesRepository, times(1))
                .getProductPrices(eq(brandId), eq(productId), eq(timestamp));
    }

    @Test
    void priceNotFound() {
        int brandId = 1;
        int productId = 35455;
        Date timestamp = new Date();

        doReturn(new ArrayList<PriceList>())
                .when(this.pricesRepository)
                .getProductPrices(anyInt(), anyInt(), any(Date.class));

        try {
            this.getProductPriceUseCase.handle(
                    brandId,
                    productId,
                    timestamp
            );
        } catch (Exception e) {
            assertInstanceOf(ResponseStatusException.class, e);
        }

        verify(this.pricesRepository, times(1))
                .getProductPrices(eq(brandId), eq(productId), eq(timestamp));
    }

    @Test
    void uniquePriceFound() {
        int brandId = 1;
        int productId = 35455;
        Date timestamp = new Date();

        PriceList mockPriceList = new PriceList(
                1,
                brandId,
                productId,
                new Timestamp(11111),
                new Timestamp(22222),
                1,
                10.0f,
                "EUR"
        );

        List<PriceList> mockResult = new ArrayList<>();
        mockResult.add(mockPriceList);

        doReturn(mockResult)
                .when(this.pricesRepository)
                .getProductPrices(anyInt(), anyInt(), any(Date.class));

        ProductPriceDTO result = this.getProductPriceUseCase.handle(
                brandId,
                productId,
                timestamp
        );

        verify(this.pricesRepository, times(1))
                .getProductPrices(eq(brandId), eq(productId), eq(timestamp));

        assertEquals(new ProductPriceDTO(mockPriceList), result);
    }

    @Test
    void multiplePricesFoundReturnsTheOneWithMorePriority() {
        int brandId = 1;
        int productId = 35455;
        Date timestamp = new Date();

        PriceList mockPriceList1 = new PriceList(
                1,
                brandId,
                productId,
                new Timestamp(11111),
                new Timestamp(22222),
                1,
                10.0f,
                "EUR"
        );

        PriceList mockPriceList2 = new PriceList(
                1,
                brandId,
                productId,
                new Timestamp(11112),
                new Timestamp(22223),
                2,
                15.0f,
                "EUR"
        );

        List<PriceList> mockResult = new ArrayList<>();
        mockResult.add(mockPriceList1);
        mockResult.add(mockPriceList2);

        doReturn(mockResult)
                .when(this.pricesRepository)
                .getProductPrices(anyInt(), anyInt(), any(Date.class));

        ProductPriceDTO result = this.getProductPriceUseCase.handle(
                brandId,
                productId,
                timestamp
        );

        verify(this.pricesRepository, times(1))
                .getProductPrices(eq(brandId), eq(productId), eq(timestamp));

        assertEquals(new ProductPriceDTO(mockPriceList2), result);
    }
}