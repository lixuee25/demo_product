package com.products.dto.response;

import java.math.BigDecimal;

public class AddProductSKUResponse {

    private int id;
    private String skuCode;
    private String skuName;
    private BigDecimal price;
    private String barCode;
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   
    public AddProductSKUResponse(int id, String skuCode, String skuName, BigDecimal price, String barCode, String message) {
        this.id = id;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.price = price;
        this.barCode = barCode;
        this.message = message;
    }

  
    public AddProductSKUResponse(String message) {
        this.message = message;
    }

    public AddProductSKUResponse() {

    }
}
