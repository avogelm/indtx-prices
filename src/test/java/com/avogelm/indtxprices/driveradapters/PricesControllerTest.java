package com.avogelm.indtxprices.driveradapters;

import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.ProductPriceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PricesControllerTest {

    @MockBean
    GetProductPriceUseCase getProductPriceUseCase;

    @Autowired
    private MockMvc mockMvc;


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
    void getProductPriceWithNegativePathVariable() throws Exception {
        int brandId = -1;
        int productId = 35455;

        Date timestamp = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
        String formattedTimestamp = formatter.format(timestamp);

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}?timestamp={timestamp}",
                        brandId,
                        productId,
                        formattedTimestamp
                )
        );

        result.andExpect(status().isBadRequest());
        verifyNoInteractions(getProductPriceUseCase);
    }

    @Test
    void getProductPriceInternalErrorInUseCase() throws Exception {
        int brandId = 1;
        int productId = 35455;

        Date timestamp = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
        String formattedTimestamp = formatter.format(timestamp);

        doThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(this.getProductPriceUseCase)
                .handle(anyInt(), anyInt(), any(Date.class));

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}?timestamp={timestamp}",
                        brandId,
                        productId,
                        formattedTimestamp
                )
        );

        result.andExpect(status().isInternalServerError());
        verify(getProductPriceUseCase, times(1))
                .handle(eq(brandId), eq(productId), eq(formatter.parse(formattedTimestamp)));
    }

    @Test
    void getProductPrice() throws Exception {
        int brandId = 1;
        int productId = 35455;

        Date timestamp = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
        String formattedTimestamp = formatter.format(timestamp);

        doReturn(new ProductPriceDTO())
                .when(this.getProductPriceUseCase)
                .handle(anyInt(), anyInt(), any(Date.class));

        ResultActions result = mockMvc.perform(
                get("/api/prices/{brandId}/{productId}?timestamp={timestamp}",
                        brandId,
                        productId,
                        formattedTimestamp
                )
        );

        result.andExpect(status().isOk());
        verify(getProductPriceUseCase, times(1))
                .handle(eq(brandId), eq(productId), eq(formatter.parse(formattedTimestamp)));
    }
}