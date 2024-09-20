package com.avogelm.indtxprices.application.driverports;

import com.avogelm.indtxprices.application.core.domain.model.PriceList;

import java.util.Date;

public class ProductPriceDTO {
    private int productId;
    private int brandId;
    private int priceList;
    private Date startDate;
    private Date endDate;
    private float price;

    public ProductPriceDTO() {
        //TODO from entity
    }

    public ProductPriceDTO(PriceList priceList) {
        this.priceList = priceList.getPriceList();
        this.brandId = priceList.getBrandId();
        this.productId = priceList.getProductId();
        this.startDate = priceList.getStartDate();
        this.endDate = priceList.getEndDate();
        this.price = priceList.getPrice();
    }

    public int getProductId() {
        return productId;
    }

    public int getBrandId() {
        return brandId;
    }

    public int getPriceList() {
        return priceList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ProductPriceDTO objMatch)) {
            return false;
        }

        return this.priceList == objMatch.getPriceList()
            && this.brandId == objMatch.getBrandId()
            && this.productId == objMatch.getProductId()
            && this.startDate == objMatch.getStartDate()
            && this.endDate == objMatch.getEndDate()
            && this.price == objMatch.getPrice();
    }
}
