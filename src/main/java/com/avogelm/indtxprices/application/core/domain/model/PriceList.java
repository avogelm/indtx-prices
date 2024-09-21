package com.avogelm.indtxprices.application.core.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

    @Column(name = "priority")
    private int priority;

    @Column(name = "price")
    private float price;

    @Column(name = "curr")
    private String curr;

    public PriceList() {}

    public PriceList(int priceList, int brandId, int productId, LocalDateTime startDate, LocalDateTime endDate, int priority, float price, String curr) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public void setCurr(String curr) {
        this.curr = curr;
    }
}
