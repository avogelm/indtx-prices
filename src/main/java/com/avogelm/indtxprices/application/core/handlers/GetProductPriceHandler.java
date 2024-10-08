package com.avogelm.indtxprices.application.core.handlers;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;
import com.avogelm.indtxprices.application.drivenports.PricesRepository;
import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.dto.ProductPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
public class GetProductPriceHandler implements GetProductPriceUseCase {

    @Autowired
    private PricesRepository pricesRepository;

    @Override
    public ProductPriceDTO handle(int brandId, int productId, LocalDateTime dateTime) {
        PriceList result = this.pricesRepository.getProductPrice(
                brandId,
                productId,
                dateTime
        );
        if (result == null) {
            String NOT_FOUND_MESSAGE = "Price List could not be found for product with ID %d of brand with ID %s.";
            String message = String.format(NOT_FOUND_MESSAGE, productId, brandId);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    message
            );
        }

        return new ProductPriceDTO(result);
    }
}
