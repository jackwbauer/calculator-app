package com.example.calculator;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.calculator.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private double valueOne = Double.NaN;
    private double valueTwo;

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

        //can display up to 10 decimal places
        decimalFormat = new DecimalFormat("#.##########");

        // Bindings

        binding.button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("0");
            }
        });

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("1");
//                binding.equation.setText(binding.equation.getText() + "1");
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("2");
//                binding.equation.setText(binding.equation.getText() + "2");
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("3");
//                binding.equation.setText(binding.equation.getText() + "3");
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("4");
//                binding.equation.setText(binding.equation.getText() + "4");
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("5");
//                binding.equation.setText(binding.equation.getText() + "5");
            }
        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("6");
//                binding.equation.setText(binding.equation.getText() + "6");
            }
        });

        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("7");
//                binding.equation.setText(binding.equation.getText() + "7");
            }
        });

        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("8");
//                binding.equation.setText(binding.equation.getText() + "8");
            }
        });

        binding.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToEquation("9");
//                binding.equation.setText(binding.equation.getText() + "9");
            }
        });

        binding.buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.equation.getText().toString().contains(".")) {
                    appendToEquation(".");
//                    binding.equation.setText(binding.equation.getText() + ".");
                }
            }
        });

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"".equals(binding.equation.getText().toString())) {
                    computeCalculation();
                    CURRENT_ACTION = ADDITION;
                    appendToEquation("+");
//                    binding.equation.setText(decimalFormat.format(valueOne) + " + ");
//                    binding.equation.setText(null);
                }
            }
        });

        binding.buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(binding.equation.getText().toString())) {
                    computeCalculation();
                    CURRENT_ACTION = SUBTRACTION;
                    appendToEquation("-");
//                binding.equation.setText(decimalFormat.format(valueOne) + " - ");
//                binding.equation.setText(null);
                }
            }
        });

        binding.buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(binding.equation.getText().toString())) {
                    computeCalculation();
                    CURRENT_ACTION = MULTIPLICATION;
                    appendToEquation(" * ");
//                binding.equation.setText(decimalFormat.format(valueOne) + " * ");
//                binding.equation.setText(null);
                }
            }
        });

        binding.buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(binding.equation.getText().toString())) {
                    computeCalculation();
                    CURRENT_ACTION = DIVISION;
                    appendToEquation(" / ");
//                binding.equation.setText(decimalFormat.format(valueOne) + " / ");
//                binding.equation.setText(null);
                }
            }
        });

        binding.buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeCalculation();
//                binding.resultTextView.setText(binding.resultTextView.getText().toString() + decimalFormat.format(valueTwo) + " = " + decimalFormat.format(valueOne));
                updateResult(valueOne);
                //valueOne = Double.NaN;
                CURRENT_ACTION = NONE;
            }
        });

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.resultTextView.setText(null);
                binding.equation.setText(null);
            }
        });

        binding.buttonClearEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.equation.setText(null);
            }
        });

    }

    private void parseEquation() {

    }

    private void computeEquation() {

    }

    private void appendToEquation(String value) {
        binding.equation.setText(binding.equation.getText() + value);
    }

    private void updateResult(double value) {
        binding.resultTextView.setText(String.valueOf(value));
    }

    private void computeCalculation() {
        if(Double.isNaN(valueOne)) {
            try {
                valueOne = Double.parseDouble(binding.equation.getText().toString());
            } catch(Exception e) {}
        } else {
            valueTwo = Double.parseDouble(binding.equation.getText().toString());
            binding.equation.setText(null);

            if(CURRENT_ACTION == ADDITION) {
                valueOne += valueTwo;
            } else if(CURRENT_ACTION == SUBTRACTION) {
                valueOne -= valueTwo;
            } else if(CURRENT_ACTION == MULTIPLICATION) {
                valueOne *= valueTwo;
            } else if(CURRENT_ACTION == DIVISION) {
                valueOne /= valueTwo;
            }
        }
    }
}
