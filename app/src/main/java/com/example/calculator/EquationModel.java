package com.example.calculator;

import android.databinding.ObservableField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class EquationModel {
    public ObservableField<String> fullEquation = new ObservableField<>();
    public Double valueOne;
    public Double valueTwo;
    public char operator;

    private Double solution;
    private boolean equaled = false;

    public EquationModel() {
        this.fullEquation.set("");
        this.valueOne = Double.NaN;
        this.valueTwo = Double.NaN;
    }

    public void updateFullEquation() {
        String equation = "";
        if(!Double.isNaN(this.valueOne)) {
            if (this.valueOne % 1 == 0) {
                equation += String.valueOf(Math.round(this.valueOne));
            } else {
                equation += this.valueOne.toString();
            }

            if (this.operator != '\u0000') {  // '\u0000' is the default char value
                equation += this.operator;
                if (!Double.isNaN(this.valueTwo)) {
                    if (this.valueTwo % 1 == 0) {
                        equation += String.valueOf(Math.round(this.valueTwo));
                    } else {
                        equation += valueTwo.toString();
                    }
                }
            }
        }
        this.fullEquation.set(equation);
    }

    public void updateValue(int digit) {
        String digitString = String.valueOf(digit);
        String valueString = "";

        if(operator == '\u0000') {
            if(equaled) {
                this.valueOne = new Double(digit);
                equaled = false;
            } else if(!Double.isNaN(this.valueOne)) {
                if(this.valueOne % 1 == 0) {
                    valueString = String.valueOf(Math.round(this.valueOne));
                } else {
                    valueString = this.valueOne.toString();
                }
            }
            this.valueOne = Double.valueOf(valueString + digitString);
        } else {
            equaled = false;
            if(!Double.isNaN(this.valueTwo)) {
                if(this.valueTwo % 1 == 0) {
                    valueString = String.valueOf(Math.round(this.valueTwo));
                } else {
                    valueString = this.valueTwo.toString();
                }
            }
            this.valueTwo = Double.valueOf(valueString + digitString);
        }

        updateFullEquation();
    }

    public void addDecimalToValue() {

    }

    public void updateOperator(char operator) {
        if (!Double.isNaN(this.valueTwo)) {
            solveEquation();
        }

        this.operator = operator;
        updateFullEquation();
    }

    public void solveEquation() {
        if(!Double.isNaN(this.valueTwo)) {
            calculateEquation();
            clearEquation();
            this.valueOne = this.solution;
            updateFullEquation();
            equaled = true;
        }
    }

    public void calculateEquation() {
        if(!Double.isNaN(this.valueOne) && !Double.isNaN(this.valueTwo)) {
            Double solution = this.valueOne;
            switch (this.operator) {
                case '+':
                    solution = this.valueOne + this.valueTwo;
                    break;
                case '-':
                    solution = this.valueOne - this.valueTwo;
                    break;
                case '*':
                    solution = this.valueOne * this.valueTwo;
                    break;
                case '/':
                    if (this.valueTwo != 0) {
                        solution = this.valueOne / this.valueTwo;
                    }
                    break;
            }

            this.solution = round(solution);

        }
    }

    public void clearEquation() {
        this.valueOne = Double.NaN;
        this.valueTwo = Double.NaN;
        this.operator = '\u0000';
    }

    public void clearValue() {
        if(this.operator == '\u0000') {
            this.valueOne = Double.NaN;
        } else {
            this.valueTwo = Double.NaN;
        }
        updateFullEquation();
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(8, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
