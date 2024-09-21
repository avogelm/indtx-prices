package com.avogelm.indtxprices.application.drivenports;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;

import java.time.LocalDateTime;

public interface PricesRepository {
    public PriceList getProductPrice(int brandId, int productId, LocalDateTime dateTime);
}
