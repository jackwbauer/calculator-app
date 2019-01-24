package com.example.calculator;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.calculator.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private double valueOne = Double.NaN;
    private double valueTwo;

    private boolean hasOperator = false;

    public ObservableField<String> equation = new ObservableField<>();

    // actions
    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char NONE = '0';

    private char CURRENT_ACTION;

    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final EquationModel equationModel = new EquationModel();
        binding.setEquation(equationModel);

        //can display up to 10 decimal places
        decimalFormat = new DecimalFormat("#.##########");

        // Bindings

        binding.button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(0);
//                appendToEquation("0");
            }
        });

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(1);
//                appendToEquation("1");
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(2);
//                appendToEquation("2");
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(3);
//                appendToEquation("3");
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(4);
//                appendToEquation("4");
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(5);
//                appendToEquation("5");
            }
        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(6);
//                appendToEquation("6");
            }
        });

        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(7);
//                appendToEquation("7");
            }
        });

        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(8);
//                appendToEquation("8");
            }
        });

        binding.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateValue(9);
//                appendToEquation("9");
            }
        });

        binding.buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.addDecimalToValue();
//                if(!binding.equation.getText().toString().contains(".")) {
//                    equationModel.addDecimalToValue();
//                    appendToEquation(".");
//                }
            }
        });

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.equation.getText().toString().isEmpty()) {
                    equationModel.updateOperator('+');
//                    computeCalculation();
//
//                    // this will happen if trying place a 2nd operator without a 2nd digit
//                    if(Double.isNaN(valueTwo) && CURRENT_ACTION != ADDITION) {
//                        changeOperator("+");
//                    } else {
//                        appendToEquation("+");
//                    }
//
//                    CURRENT_ACTION = ADDITION;
                }
            }
        });

        binding.buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateOperator('-');
//                if (!"".equals(binding.equation.getText().toString()) && !Double.isNaN(valueTwo)) {
//                    computeCalculation();
//
//                    // this will happen if trying place a 2nd operator without a 2nd digit
//                    if(CURRENT_ACTION != SUBTRACTION) {
//                        changeOperator("-");
//                    } else {
//                        appendToEquation("-");
//                    }
//
//                    if(!Double.isNaN(valueTwo)) {
//
//                    }
//
//                    CURRENT_ACTION = SUBTRACTION;
//                }
            }
        });

        binding.buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateOperator('*');
//                if (!"".equals(binding.equation.getText().toString())) {
//
//                    // this will happen if trying place a 2nd operator without a 2nd digit
//                    if(Double.isNaN(valueTwo) && CURRENT_ACTION != MULTIPLICATION) {
//                        changeOperator("*");
//                    } else {
//                        appendToEquation("*");
//                    }
//
//                    CURRENT_ACTION = MULTIPLICATION;
//                }
            }
        });

        binding.buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.updateOperator('/');
//                if (!"".equals(binding.equation.getText().toString())) {
//                    computeCalculation();
//
//                    // this will happen if trying place a 2nd operator without a 2nd digit
//                    if(Double.isNaN(valueTwo) && CURRENT_ACTION != DIVISION) {
//                        changeOperator("/");
//                    } else {
//                        appendToEquation("/");
//                    }
//
//                    CURRENT_ACTION = DIVISION;
//                }
            }
        });

        binding.buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.solveEquation();
//                computeCalculation();
            }
        });

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.clearEquation();
                equationModel.updateFullEquation();
//                binding.equation.setText(null);
//                valueOne = Double.NaN;
//                valueTwo = Double.NaN;
            }
        });

        binding.buttonClearEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equationModel.clearValue();
//                binding.equation.setText(null);
            }
        });

    }

//    private void appendToEquation(String value) {
//        binding.equation.setText(binding.equation.getText() + value);
//    }
//
//    private void changeOperator(String operator) {
//        binding.equation.setText(decimalFormat.format(valueOne) + operator);
//    }

//    private Double getValueTwo() {
//        String equationString = binding.equation.getText().toString();
//        int operatorIndex = equationString.indexOf(CURRENT_ACTION);
//        if(operatorIndex == -1) {
//            return Double.NaN;
//        }
//        String valueString = equationString.substring(operatorIndex + 1);
//        if(valueString.isEmpty()) {
//            return Double.NaN;
//        }
//        return Double.parseDouble(valueString);
//    }

//    private void computeCalculation() {
//        if(Double.isNaN(valueOne)) {
//            try {
//                valueOne = Double.parseDouble(binding.equation.getText().toString());
//            } catch(Exception e) {}
//        } else {
//            valueTwo = getValueTwo();
//            if(!Double.isNaN(valueTwo)) {
//                if (CURRENT_ACTION == ADDITION) {
//                    valueOne += valueTwo;
//                } else if (CURRENT_ACTION == SUBTRACTION) {
//                    valueOne -= valueTwo;
//                } else if (CURRENT_ACTION == MULTIPLICATION) {
//                    valueOne *= valueTwo;
//                } else if (CURRENT_ACTION == DIVISION) {
//                    valueOne /= valueTwo;
//                }
//                binding.equation.setText(decimalFormat.format(valueOne));
//                CURRENT_ACTION = NONE;
//            }
//        }
//    }
}
