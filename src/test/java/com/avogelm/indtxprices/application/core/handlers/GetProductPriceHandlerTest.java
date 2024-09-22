package com.avogelm.indtxprices.application.core.handlers;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;
import com.avogelm.indtxprices.application.drivenports.PricesRepository;
import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.dto.ProductPriceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

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
        LocalDateTime dateTime = LocalDateTime.now();

        doThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(this.pricesRepository)
                .getProductPrice(anyInt(), anyInt(), any(LocalDateTime.class));

        try {
            this.getProductPriceUseCase.handle(
                    brandId,
                    productId,
                    dateTime
            );
        } catch (Exception e) {
            assertInstanceOf(ResponseStatusException.class, e);
        }

        verify(this.pricesRepository, times(1))
                .getProductPrice(eq(brandId), eq(productId), eq(dateTime));
    }

    @Test
    void priceNotFound() {
        int brandId = 1;
        int productId = 35455;
        LocalDateTime dateTime = LocalDateTime.now();

        doReturn(null)
                .when(this.pricesRepository)
                .getProductPrice(anyInt(), anyInt(), any(LocalDateTime.class));

        try {
            this.getProductPriceUseCase.handle(
                    brandId,
                    productId,
                    dateTime
            );
        } catch (Exception e) {
            assertInstanceOf(ResponseStatusException.class, e);
        }

        verify(this.pricesRepository, times(1))
                .getProductPrice(eq(brandId), eq(productId), eq(dateTime));
    }

    @Test
    void priceFound() {
        int brandId = 1;
        int productId = 35455;
        LocalDateTime dateTime = LocalDateTime.now();

        PriceList mockResult = new PriceList(
                1,
                brandId,
                productId,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1,
                10.0f,
                "EUR"
        );


        doReturn(mockResult)
                .when(this.pricesRepository)
                .getProductPrice(anyInt(), anyInt(), any(LocalDateTime.class));

        ProductPriceDTO result = this.getProductPriceUseCase.handle(
                brandId,
                productId,
                dateTime
        );

        verify(this.pricesRepository, times(1))
                .getProductPrice(eq(brandId), eq(productId), eq(dateTime));

        assertEquals(new ProductPriceDTO(mockResult), result);
    }

}