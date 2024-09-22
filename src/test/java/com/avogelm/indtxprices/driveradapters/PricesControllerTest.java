package com.avogelm.indtxprices.driveradapters;

import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.dto.ProductPriceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class PricesControllerTest {

    @MockBean
    GetProductPriceUseCase getProductPriceUseCase;

    @Autowired
    private MockMvc mockMvc;

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Test
    void getProductPriceWithErrorInPath() throws Exception{
        int brandId = 1;
        int productId = 35455;

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}{productId}",
                        brandId,
                        productId
                )
        );

        result.andExpect(status().isNotFound());
        verifyNoInteractions(getProductPriceUseCase);
    }

    @Test
    void getProductPriceWithEmptyTimestamp() throws Exception{
        int brandId = 1;
        int productId = 35455;

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}",
                        brandId,
                        productId
                )
        );

        result.andExpect(status().isBadRequest());
        verifyNoInteractions(getProductPriceUseCase);
    }

    @Test
    void getProductPriceWithTimestampWithWrongFormat() throws Exception{
        int brandId = 1;
        int productId = 35455;

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}?timestamp={timestamp}",
                        brandId,
                        productId,
                        LocalDateTime.now().format(
                                DateTimeFormatter.ofPattern("yyyy")
                        )
                )
        );

        result.andExpect(status().isBadRequest());
        verifyNoInteractions(getProductPriceUseCase);
    }

    @Test
    void getProductPriceWithNegativePathVariable() throws Exception {
        int brandId = -1;
        int productId = 35455;

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}?timestamp={timestamp}",
                        brandId,
                        productId,
                        LocalDateTime.now().format(df)
                )
        );

        result.andExpect(status().isBadRequest());
        verifyNoInteractions(getProductPriceUseCase);
    }

    @Test
    void getProductPriceInternalErrorInUseCase() throws Exception {
        int brandId = 1;
        int productId = 35455;

        LocalDateTime dateTime = LocalDateTime.now();

        doThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(this.getProductPriceUseCase)
                .handle(anyInt(), anyInt(), any(LocalDateTime.class));

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}?timestamp={timestamp}",
                        brandId,
                        productId,
                        dateTime.format(df)
                )
        );

        result.andExpect(status().isInternalServerError());
        verify(getProductPriceUseCase, times(1))
                .handle(eq(brandId), eq(productId), eq(dateTime.truncatedTo(ChronoUnit.SECONDS)));
    }

    @Test
    void getProductPrice() throws Exception {
        int brandId = 1;
        int productId = 35455;

        LocalDateTime dateTime = LocalDateTime.now();

        doReturn(new ProductPriceDTO())
                .when(this.getProductPriceUseCase)
                .handle(anyInt(), anyInt(), any(LocalDateTime.class));

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}?timestamp={timestamp}",
                        brandId,
                        productId,
                        dateTime.format(df)
                )
        );

        result.andExpect(status().isOk());
        verify(getProductPriceUseCase, times(1))
                .handle(eq(brandId), eq(productId), eq(dateTime.truncatedTo(ChronoUnit.SECONDS)));
    }
}