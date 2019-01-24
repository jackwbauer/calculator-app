package com.example.calculator;

import android.databinding.ObservableField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class EquationModel {
    public ObservableField<String> fullEquation = new ObservableField<>();
//    public Double valueOne;
    public String valueOne;
//    public Double valueTwo;
    public String valueTwo;
    public char operator;

    private Double solution;
    private boolean equaled = false;
    private boolean activeValue = false; // false is valueOne, true is valueTwo
    private int trailingZeroesOne = 0;
    private int trailingZeroesTwo = 0;
    private boolean hasDecimalOne = false;
    private boolean hasDecimalTwo = false;

    public EquationModel() {
        this.fullEquation.set("");
//        this.valueOne = Double.NaN;
        //this.valueTwo = Double.NaN;
        this.valueOne = "";
        this.valueTwo = "";
    }

    public void updateFullEquation() {
        String equation = "";
        if(!this.valueOne.isEmpty()) {
            if (Double.valueOf(this.valueOne) % 1 == 0 || activeValue) {
                equation += String.valueOf(Integer.parseInt(this.valueOne));
            } else {
                equation += this.valueOne;
            }

            if (this.operator != '\u0000') {  // '\u0000' is the default char value
                equation += this.operator;
                if (!this.valueTwo.isEmpty()) {
                    if (Double.valueOf(this.valueTwo) % 1 == 0) {
                        equation += String.valueOf(Integer.parseInt(this.valueTwo));
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

        if(operator == '\u0000') {
            if(equaled) {
//                this.valueOne = new Double(digit);
                this.valueOne = String.valueOf(digit);
                equaled = false;
            } else if(!this.valueOne.isEmpty()) {
                if(Double.valueOf(this.valueOne) % 1 == 0) {
                    valueString = String.valueOf(Math.round(Double.valueOf(this.valueOne)));
                } else {
                    valueString = this.valueOne;
                }
            }
            this.valueOne = valueString + digitString;
        } else {
            equaled = false;
            if(!this.valueTwo.isEmpty()) {
                if(Double.valueOf(this.valueTwo) % 1 == 0) {
                    valueString = String.valueOf(Integer.parseInt(this.valueTwo));
                } else {
                    valueString = this.valueTwo;
                }
            }
            this.valueTwo = valueString + digitString;
        }

        updateFullEquation();
    }

    public void addDecimalToValue() {
        if(!activeValue) {
            if(this.valueOne.isEmpty()) {
                this.valueOne += "0.";
            } else {
                this.valueOne += ".";
            }
        } else {
            if(this.valueTwo.isEmpty()) {
                this.valueTwo += "0.";
            } else {
                this.valueTwo += ".";
            }
        }
    }

    public void updateOperator(char operator) {
        if (!this.valueTwo.isEmpty()) {
            solveEquation();
        }

        this.operator = operator;
        updateFullEquation();
    }

    public void solveEquation() {
        if(!this.valueTwo.isEmpty()) {
            calculateEquation();
            clearEquation();
            this.valueOne = String.valueOf(this.solution);
            updateFullEquation();
            equaled = true;
        }
    }

    public void calculateEquation() {
        if(!this.valueOne.isEmpty() && !this.valueTwo.isEmpty()) {
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
    }

    public void clearValue() {
        if(this.operator == '\u0000') {
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
}
