package com.iit.icw.governmenttaxsystem.dto;

import javafx.beans.property.SimpleStringProperty;

public class ProfitTableDto extends TableDto {
    private SimpleStringProperty profitAmount;
    private SimpleStringProperty profitType;

    public ProfitTableDto(String lineId, String itemCode, String intPrice, String discount, String salePrice, String qty, String billId, String lineTotal, String checkSum, String profitAmount, String profitType) {
        super(lineId, itemCode, intPrice, discount, salePrice, qty, billId, lineTotal, checkSum);
        this.profitAmount = new SimpleStringProperty(profitAmount);
        this.profitType = new SimpleStringProperty(profitType);
    }

    public String getProfitType() {
        return profitType.get();
    }

    public SimpleStringProperty profitTypeProperty() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = new SimpleStringProperty(profitType);
    }

    public String getProfitAmount() {
        return profitAmount.get();
    }

    public SimpleStringProperty profitAmountProperty() {
        return profitAmount;
    }

    public void setProfitAmount(String profitAmount) {
        this.profitAmount = new SimpleStringProperty(profitAmount);
    }
}
