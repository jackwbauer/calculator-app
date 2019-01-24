package com.example.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValueModel {
    public Double value;
    public boolean hasDecimal;

    private String valueString;
    private int trailingDecimals;

    public ValueModel() {
        this.value = Double.NaN;
        this.hasDecimal = false;
        this.trailingDecimals = 0;
    }

    public Double getRoundedValue() {
        return round(this.value);
    }

    public void appendToValue(String value) {
        if(this.hasDecimal) {
            if(value == ".") {
                return;
            }
            if(value != "0") {
                this.value = Double.valueOf(this.value.toString() + value);
            }
            this.valueString += value;
        }
        this.valueString += value;
        this.value = Double.valueOf(this.value.toString() + value);
    }

    public int toInt() {
        return (int) Math.round(this.value);
    }

    public String toString() {
        return valueString;
    }

    public void addTrailingDecimal() {
        this.trailingDecimals++;
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(8, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
