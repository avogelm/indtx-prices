package com.avogelm.indtxprices.application.driverports;

import com.avogelm.indtxprices.application.driverports.dto.ProductPriceDTO;

import java.time.LocalDateTime;

public interface GetProductPriceUseCase {
    public ProductPriceDTO handle(int brandId, int productId, LocalDateTime dateTime);
}
