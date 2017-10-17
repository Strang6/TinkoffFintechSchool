package com.strang6.tinkofffintechschool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity
        implements OperandFragment.OnOperandEnteredListener, OperationFragment.OnOperationEnteredListener {

    private Button resultButton, operationButton, secondOperandButton;
    private double firstOperand, secondOperand;
    private String operation;
    private boolean isChangeArgs = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultButton = (Button) findViewById(R.id.resultButton);
        operationButton = (Button) findViewById(R.id.operationButton);
        secondOperandButton = (Button) findViewById(R.id.secondOperandButton);
        if (savedInstanceState == null) {
            Fragment firstOperandFragment = new OperandFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, firstOperandFragment, "firstOperandFragment")
                    .commit();
        }
    }

    public void onClickFragmentButton(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.firstOperandButton: {
                Fragment firstOperandFragment = getSupportFragmentManager().findFragmentByTag("firstOperandFragment");
                fragmentTransaction.replace(R.id.fragment_container, firstOperandFragment);
                break;
            }
            case R.id.secondOperandButton: {
                Fragment secondOperandFragment = getSupportFragmentManager().findFragmentByTag("secondOperandFragment");
                if (secondOperandFragment == null)
                    secondOperandFragment = new OperandFragment();
                fragmentTransaction.replace(R.id.fragment_container, secondOperandFragment, "secondOperandFragment");
                break;
            }
            case R.id.operationButton: {
                Fragment operationFragment = getSupportFragmentManager().findFragmentByTag("operationFragment");
                if (operationFragment == null)
                    operationFragment = new OperationFragment();
                fragmentTransaction.replace(R.id.fragment_container, operationFragment, "operationFragment");
                break;
            }
            case R.id.resultButton: {
                if (operation.equals("/") && secondOperand == 0) {
                    String error = "Делить на 0 нельзя, выберите другую операцию или введите другой операнд2";
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                } else {
                    Fragment resultFragment = getSupportFragmentManager().findFragmentByTag("resultFragment");
                    if (resultFragment == null || isChangeArgs) {
                        resultFragment = ResultFragment.newInstance(firstOperand, secondOperand, operation);
                        isChangeArgs = false;
                    }
                    fragmentTransaction.replace(R.id.fragment_container, resultFragment, "resultFragment");
                }
                break;
            }
        }
        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void onOperandEntered(double operand) {
        isChangeArgs = true;
        Fragment firstOperandFragment = getSupportFragmentManager().findFragmentByTag("firstOperandFragment");
        Fragment secondOperandFragment = getSupportFragmentManager().findFragmentByTag("secondOperandFragment");
        if (firstOperandFragment.isResumed()) {
            firstOperand = operand;
            if (!secondOperandButton.isEnabled())
                secondOperandButton.setEnabled(true);
            onClickFragmentButton(secondOperandButton);
        } else if (secondOperandFragment.isResumed()) {
            secondOperand = operand;
            if (!operationButton.isEnabled())
                operationButton.setEnabled(true);
            onClickFragmentButton(operationButton);
        }
    }

    @Override
    public void onOperationEntered(String operation) {
        this.operation = operation;
        isChangeArgs = true;
        if (operation.equals("/") && secondOperand == 0) {
            String error = "Делить на 0 нельзя, выберите другую операцию или введите другой операнд2";
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        } else {
            if (!resultButton.isEnabled())
                resultButton.setEnabled(true);
            onClickFragmentButton(resultButton);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("firstOperand", firstOperand);
        outState.putDouble("secondOperand", secondOperand);
        outState.putString("operation", operation);
        outState.putBoolean("isChangeArgs", isChangeArgs);
        outState.putBoolean("isResultButtonEnabled", resultButton.isEnabled());
        outState.putBoolean("isOperationButtonEnabled", operationButton.isEnabled());
        outState.putBoolean("isSecondOperandButtonEnabled", secondOperandButton.isEnabled());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstOperand = savedInstanceState.getDouble("firstOperand");
        secondOperand = savedInstanceState.getDouble("secondOperand");
        operation = savedInstanceState.getString("operation");
        isChangeArgs = savedInstanceState.getBoolean("isChangeArgs");
        resultButton.setEnabled(savedInstanceState.getBoolean("isResultButtonEnabled"));
        operationButton.setEnabled(savedInstanceState.getBoolean("isOperationButtonEnabled"));
        secondOperandButton.setEnabled(savedInstanceState.getBoolean("isSecondOperandButtonEnabled"));
    }
}
