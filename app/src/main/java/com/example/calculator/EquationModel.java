package com.example.calculator;

import android.databinding.ObservableField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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
    private DecimalFormat decimalFormat = new DecimalFormat("#.#######E0");

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
                equation += exponentialNotation(String.valueOf(Math.round(Double.valueOf(this.valueOne))));
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
        this.equaled = false;
        updateFullEquation();
    }

    public void invertValue() {
        if(!this.activeValue && !this.valueOne.isEmpty()) {
            if(this.valueOne.charAt(0) == '-') {
                this.valueOne = this.valueOne.substring(1);
            } else {
                this.valueOne = "-" + this.valueOne;
            }
        } else if(!this.valueTwo.isEmpty()){
            if(this.valueTwo.charAt(0) == '-') {
                this.valueTwo = this.valueTwo.substring(1);
            } else {
                this.valueTwo = "-" + this.valueTwo;
            }
        }
        updateFullEquation();
    }

    public void updateOperator(char operator) {
        if (!this.valueTwo.isEmpty()) {
            solveEquation();
        } else if (this.valueOne.charAt(this.valueOne.length() - 1) == '.') {
            this.valueOne += "0";
        }

        this.operator = operator;
        this.activeValue = true;
        updateFullEquation();
    }

    public void solveEquation() {
        if (!this.valueTwo.isEmpty()) {
            calculateEquation();
            clearEquation();
            this.valueOne = removeTailingDotZero(exponentialNotation(this.solution));
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

    public void backspace() {
        char toRemove;
        if(!this.activeValue && !this.valueOne.isEmpty()) {
            toRemove = this.valueOne.charAt(this.valueOne.length() - 1);
            if(toRemove == '.') {
                this.hasDecimalOne = false;
            }
            this.valueOne = this.valueOne.substring(0, this.valueOne.length() - 1);
        } else if (!this.valueTwo.isEmpty()) {
            toRemove = this.valueTwo.charAt(this.valueTwo.length() - 1);
            if(toRemove == '.') {
                this.hasDecimalTwo = false;
            }
            this.valueTwo = this.valueTwo.substring(0, this.valueTwo.length() - 1);
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

    private String removeTailingDotZero(String value) {
        String valueSubStr = value.substring(0, value.length() - 2);
        if(value.equals(valueSubStr + ".0")) {
            return valueSubStr;
        }

        return value;
    }

    private boolean isZero(String value) {
        Double valueDbl = Double.valueOf(value);
        if(valueDbl == 0.0) {
            return true;
        }

        return false;
    }

    private String exponentialNotation(String value) {
        Double valueDbl = Double.valueOf(value);
        if(valueDbl / 10000000 > 1) {
            return decimalFormat.format(Double.valueOf(value));
        }

        if(valueDbl * 10000000 < 1) {
            return decimalFormat.format(Double.valueOf(value));
        }

        return value;
    }

    private String exponentialNotation(Double value) {
        if(value / 10000000 > 1) {
            return decimalFormat.format(Double.valueOf(value));
        }

        if(value * 10000000 < 1) {
            return decimalFormat.format(Double.valueOf(value));
        }

        return value.toString();
    }
}
