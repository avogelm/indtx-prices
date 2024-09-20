package com.avogelm.indtxprices.application.core.domain.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "prices")
public class PriceList {
    @Column(name = "price_list")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceList;

    @Column(name = "brand_id")
    private int brandId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private Date startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    private Date endDate;

    @Column(name = "priority")
    private int priority;

    @Column(name = "price")
    private float price;

    @Column(name = "curr")
    private String curr;

    public PriceList() {}

    public PriceList(int priceList, int brandId, int productId, Timestamp startDate, Timestamp endDate, int priority, float price, String curr) {
        this.priceList = priceList;
        this.brandId = brandId;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.price = price;
        this.curr = curr;
    }

    public int getPriceList() {
        return priceList;
    }

    public void setPriceList(int priceList) {
        this.priceList = priceList;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }
}
