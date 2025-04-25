package com.iit.icw.governmenttaxsystem.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableDto {
    private SimpleStringProperty lineId;
    private SimpleStringProperty itemCode;
    private SimpleStringProperty intPrice;
    private SimpleStringProperty discount;
    private SimpleStringProperty salePrice;
    private SimpleStringProperty qty;
    private SimpleStringProperty billId;
    private SimpleStringProperty lineTotal;
    private SimpleStringProperty checkSum;


    public TableDto(String lineId, String itemCode, String intPrice, String discount, String salePrice, String qty, String billId, String lineTotal, String checkSum) {
        this.lineId = new SimpleStringProperty(lineId);
        this.itemCode = new SimpleStringProperty(itemCode);
        this.intPrice = new SimpleStringProperty(intPrice);
        this.discount = new SimpleStringProperty(discount);
        this.salePrice = new SimpleStringProperty(salePrice);
        this.qty = new SimpleStringProperty(qty);
        this.billId = new SimpleStringProperty(billId);
        this.lineTotal = new SimpleStringProperty(lineTotal);
        this.checkSum = new SimpleStringProperty(checkSum);
    }


    public String getLineId() {
        return lineId.get();
    }

    public void setLineId(String lineId) {
        this.lineId = new SimpleStringProperty(lineId);
    }

    public StringProperty lineIdProperty() {
        return lineId;
    }

    public String getItemCode() {
        return itemCode.get();
    }

    public void setItemCode(String itemCode) {
        this.itemCode = new SimpleStringProperty(itemCode);
    }

    public StringProperty itemCodeProperty() {
        return itemCode;
    }

    public String getIntPrice() {
        return intPrice.get();
    }

    public void setIntPrice(String intPrice) {
        this.intPrice = new SimpleStringProperty(intPrice);
    }

    public StringProperty intPriceProperty() {
        return intPrice;
    }


    public String getDiscount() {
        return discount.get();
    }

    public void setDiscount(String discount) {
        this.discount = new SimpleStringProperty(discount);
    }

    public StringProperty discountProperty() {
        return discount;
    }

    public String getSalePrice() {
        return salePrice.get();
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = new SimpleStringProperty(salePrice);
    }

    public StringProperty salePriceProperty() {
        return salePrice;
    }

    public String getQty() {
        return qty.get();
    }

    public void setQty(String qty) {
        this.qty = new SimpleStringProperty(qty);
    }

    public StringProperty qtyProperty() {
        return qty;
    }

    public String getBillId() {
        return billId.get();
    }

    public void setBillId(String billId) {
        this.billId = new SimpleStringProperty(billId);
    }

    public StringProperty billIdProperty() {
        return billId;
    }

    public String getLineTotal() {
        return lineTotal.get();
    }

    public void setLineTotal(String lineTotal) {
        this.lineTotal = new SimpleStringProperty(lineTotal);
    }

    public StringProperty lineTotalProperty() {
        return lineTotal;
    }

    public String getCheckSum() {
        return checkSum.get();
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = new SimpleStringProperty(checkSum);
    }

    public StringProperty checkSumProperty() {
        return checkSum;
    }
}
