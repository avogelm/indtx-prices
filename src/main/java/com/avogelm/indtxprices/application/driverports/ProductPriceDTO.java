package com.avogelm.indtxprices.application.driverports;

import java.util.Date;

public class ProductPriceDTO {
    private int productId;
    private int brandId;
    private int priceList;
    private Date timestamp;
    private float price;

    public ProductPriceDTO() {
        //TODO from entity
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

    public Date getTimestamp() {
        return timestamp;
    }

    public float getPrice() {
        return price;
    }
}
