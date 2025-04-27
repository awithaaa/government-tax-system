package com.iit.icw.governmenttaxsystem.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {

    TaxCalculator taxCalculator = new TaxCalculator();


    @Test
    void calculateProfit() {
        double actualOutput = taxCalculator.calculateProfit("250", "200", "10", "2");
        double expectedOutput = 90.0;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void calculateTax() {
        double actualOutput = taxCalculator.calculateTax(250.0, 100.0, 10.0);
        double expectedOutput = 15.0;
        assertEquals(expectedOutput, actualOutput);
    }
}