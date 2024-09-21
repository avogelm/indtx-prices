package com.avogelm.indtxprices.driveradapters;

import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.ProductPriceDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/prices")
public class PricesController {

    @Autowired
    private GetProductPriceUseCase getProductPriceUseCase;

    @GetMapping("{brandId}/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductPriceDTO getProductPrice(
            @PathVariable @Min(value=1, message="Brand ID must be >= 1")
            int brandId,

            @PathVariable @Min(value=1, message="Product ID must be >= 1")
            int productId,

            @RequestParam("timestamp")
            @NotNull(message = "Application Timestamp Required")
            @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
            LocalDateTime dateTime
    ) {
        return this.getProductPriceUseCase.handle(
                brandId,
                productId,
                dateTime
        );
    }
}
