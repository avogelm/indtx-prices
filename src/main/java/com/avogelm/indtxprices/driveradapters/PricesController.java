package com.avogelm.indtxprices.driveradapters;

import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.ProductPriceDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/prices")
public class PricesController {

    @Autowired
    private GetProductPriceUseCase getProductPriceUseCase;

    @GetMapping("{brandId}/{productId}")
    public ProductPriceDTO getProductPrice(
            @PathVariable @NotBlank(message = "Brand ID is Required") int brandId,
            @PathVariable @NotBlank(message = "Product ID is Required") int productId,
            @RequestParam @NotBlank(message = "Application Timestamp Required") Date timestamp
            ) {
        return this.getProductPriceUseCase.handle(
                brandId,
                productId,
                timestamp
        );
    }
}
