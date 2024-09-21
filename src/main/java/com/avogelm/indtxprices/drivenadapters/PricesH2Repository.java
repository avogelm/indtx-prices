package com.avogelm.indtxprices.drivenadapters;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;
import com.avogelm.indtxprices.application.drivenports.PricesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PricesH2Repository extends JpaRepository<PriceList, Integer>, PricesRepository {
    @Query(
            value = "select * from prices "
            + " where brand_id=?1 "
            + " and product_id=?2 "
            + " and ?3 between start_date and end_date "
            + " order by priority desc limit 1",
            nativeQuery = true
    )
    public PriceList getProductPrice(int brandId, int productId, LocalDateTime dateTime);
}
