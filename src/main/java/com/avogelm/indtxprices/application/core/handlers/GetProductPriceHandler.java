package com.avogelm.indtxprices.application.core.handlers;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;
import com.avogelm.indtxprices.application.drivenports.PricesRepository;
import com.avogelm.indtxprices.application.driverports.GetProductPriceUseCase;
import com.avogelm.indtxprices.application.driverports.ProductPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class GetProductPriceHandler implements GetProductPriceUseCase {

    @Autowired
    private PricesRepository pricesRepository;

    @Override
    public ProductPriceDTO handle(int brandId, int productId, Date timestamp) {
        List<PriceList> priceLists = this.pricesRepository.getProductPrices(
                brandId,
                productId,
                timestamp
        );
        if (priceLists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
        priceLists.sort(Comparator.comparingInt(PriceList::getPriority));
        Collections.reverse(priceLists);

        return new ProductPriceDTO(priceLists.get(0));
    }
}
