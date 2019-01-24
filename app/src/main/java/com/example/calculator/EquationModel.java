package com.example.calculator;

import android.databinding.ObservableField;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EquationModel {
    public ObservableField<String> fullEquation = new ObservableField<>();
    public String valueOne;
    public String valueTwo;
    public char operator;

    private Double solution;
    private boolean equaled = false;
    private boolean activeValue = false; // false is valueOne, true is valueTwo
    private boolean hasDecimalOne = false;
    private boolean hasDecimalTwo = false;

    public EquationModel() {
        this.fullEquation.set("");
        this.valueOne = "";
        this.valueTwo = "";
    }

    public void updateFullEquation() {
        String equation = "";
        if (!this.valueOne.isEmpty()) {
            if (this.hasDecimalOne) {
                equation += this.valueOne;
            } else if (Double.valueOf(this.valueOne) % 1 == 0 && activeValue) {
                equation += String.valueOf(Math.round(Double.valueOf(this.valueOne)));
            } else {
                equation += this.valueOne;
            }

            if (this.operator != '\u0000') {  // '\u0000' is the default char value
                equation += this.operator;
                if (this.hasDecimalTwo) {
                    equation += this.valueTwo;
                } else if (!this.valueTwo.isEmpty()) {
                    if (Double.valueOf(this.valueTwo) % 1 == 0) {
                        equation += String.valueOf(Math.round(Double.valueOf(this.valueTwo)));
                    } else {
                        equation += this.valueTwo;
                    }
                }
            }
        }
        this.fullEquation.set(equation);
    }

    public void updateValue(int digit) {
        String digitString = String.valueOf(digit);
        String valueString = "";

        if (operator == '\u0000') {
            if (equaled) {
                this.valueOne = String.valueOf(digit);
                equaled = false;
            } else if (!this.valueOne.isEmpty()) {
                valueString = this.valueOne;
            }
            this.valueOne = valueString + digitString;
            if(Double.valueOf(this.valueOne) == 0 && !this.hasDecimalOne) {
                this.valueOne = "0";
            }
        } else {
            equaled = false;
            if (!this.valueTwo.isEmpty()) {
                valueString = this.valueTwo;
            }
            this.valueTwo = valueString + digitString;
            if(Double.valueOf(this.valueTwo) == 0 && !this.hasDecimalTwo) {
                this.valueTwo = "0";
            }
        }

        updateFullEquation();
    }

    public void addDecimalToValue() {
        if (!activeValue && !this.hasDecimalOne) {
            if (equaled) {
                this.valueOne = "0.";
            } else if (this.valueOne.isEmpty()) {
                this.valueOne += "0.";
            } else if (Double.valueOf(this.valueOne) % 1 == 0) {
                this.valueOne = cleanValue(this.valueOne) + ".";
            }
            this.hasDecimalOne = true;
        } else if (!this.hasDecimalTwo) {
            if (this.valueTwo.isEmpty()) {
                this.valueTwo += "0.";
            } else if (Double.valueOf(this.valueTwo) % 1 == 0) {
                this.valueTwo += ".";
            }
            this.hasDecimalTwo = true;
        }
        equaled = false;
        updateFullEquation();
    }

    public void updateOperator(char operator) {
        if (!this.valueTwo.isEmpty()) {
            solveEquation();
        }

        this.operator = operator;
        this.activeValue = true;
        updateFullEquation();
    }

    public void solveEquation() {
        if (!this.valueTwo.isEmpty()) {
            calculateEquation();
            clearEquation();
            this.valueOne = cleanValue(String.valueOf(this.solution));
            updateFullEquation();
            equaled = true;
        }
    }

    public void calculateEquation() {
        if (!this.valueOne.isEmpty() && !this.valueTwo.isEmpty()) {
            Double valueOne = Double.valueOf(this.valueOne);
            Double valueTwo = Double.valueOf(this.valueTwo);
            Double solution = valueOne;
            switch (this.operator) {
                case '+':
                    solution = valueOne + valueTwo;
                    break;
                case '-':
                    solution = valueOne - valueTwo;
                    break;
                case '*':
                    solution = valueOne * valueTwo;
                    break;
                case '/':
                    if (valueTwo != 0) {
                        solution = valueOne / valueTwo;
                    }
                    break;
            }

            this.solution = round(solution);

        }
    }

    public void clearEquation() {
        this.valueOne = "";
        this.valueTwo = "";
        this.operator = '\u0000';
        this.activeValue = false;
        this.hasDecimalOne = false;
        this.hasDecimalTwo = false;
    }

    public void clearValue() {
        if (this.operator == '\u0000') {
            this.valueOne = "";
        } else {
            this.valueTwo = "";
        }
        updateFullEquation();
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(8, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String cleanValue(String value) {
        if (value.charAt(value.length() - 1) == '.') {
            return value.substring(0, value.length() - 1);
        }

        if (Double.valueOf(value) % 1 == 0) {
            return String.valueOf(Math.round(Double.valueOf(value)));
        }

        return value;
    }

    private String removeLeadingZeroes(String value) {
        return value.replaceFirst("^0+(?!$)", "");
    }
}
