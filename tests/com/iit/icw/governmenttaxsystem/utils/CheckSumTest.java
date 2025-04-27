package com.iit.icw.governmenttaxsystem.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckSumTest {

    @Test
    void totalChecksum() {
        String[] testValues = {"Lemon02", "20", "0", "25.0", "1", "25.0"};
        int expectedOutput = 42;

        CheckSum checkSum = new CheckSum();
        int actualOutput = checkSum.totalChecksum(testValues);
        assertEquals(expectedOutput, actualOutput);
    }
}