package com.avogelm.indtxprices.application.drivenports;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;

import java.util.Date;
import java.util.List;

public interface PricesRepository {
    public List<PriceList> getProductPrices(int brandId, int productId, Date timestamp);
}
