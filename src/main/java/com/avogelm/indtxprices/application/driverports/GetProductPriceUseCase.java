package com.avogelm.indtxprices.application.driverports;

import java.util.Date;

public interface GetProductPriceUseCase {
    public ProductPriceDTO handle(int brandId, int productId, Date timestamp);
}
