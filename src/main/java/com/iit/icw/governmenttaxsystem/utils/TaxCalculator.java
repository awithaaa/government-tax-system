package com.iit.icw.governmenttaxsystem.utils;

public class TaxCalculator {
    public double calculateProfit(String salePrice, String intPrice, String discount, String qty) {
        double profit = (Double.parseDouble(salePrice) * Integer.parseInt(qty) - Double.parseDouble(discount) - (Double.parseDouble(intPrice) * Integer.parseInt(qty)));
        return profit;
    }

    public double calculateTax(double profit, double loss, double rate) {
        double tax = ((profit - loss) * rate)/100;
        return tax;
    }

}
