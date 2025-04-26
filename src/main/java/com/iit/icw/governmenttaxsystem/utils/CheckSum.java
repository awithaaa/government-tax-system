package com.iit.icw.governmenttaxsystem.utils;

public class CheckSum {

    public int totalChecksum(String[] row) {
        int total = 0;
        for (String r: row) {
            total = total + this.calculateChecksum(r);
        }
        return total;
    }

    private int calculateChecksum(String transactionLine) {
        int countUpper = 0;
        int countLower = 0;
        int countNumbers = 0;

        for (char c : transactionLine.toCharArray()) {
            if (Character.isUpperCase(c)) {
                countUpper += 2;
            } else if (Character.isLowerCase(c)) {
                countLower += 1;
            } else if (Character.isDigit(c)) {
                countNumbers += 3;
            }
        }

        int checksum = countUpper + countLower + countNumbers;
        return checksum;
    }

}
