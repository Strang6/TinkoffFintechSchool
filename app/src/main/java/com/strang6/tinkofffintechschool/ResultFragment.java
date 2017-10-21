package com.strang6.tinkofffintechschool;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    private static String firstOperandKey = "firstOperand",
                          secondOperandKey = "secondOperand",
                          operationKey = "operation";

    public ResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        TextView operationTextView = (TextView) view.findViewById(R.id.operationTextView);
        TextView resultTextView = (TextView) view.findViewById(R.id.resultTextView);
        operationTextView.setText(getExpression());
        resultTextView.setText(Double.toString(getResult()));
        return view;
    }

    public static ResultFragment newInstance(double firstOperand, double secondOperand, String operation) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putDouble(firstOperandKey, firstOperand);
        args.putDouble(secondOperandKey, secondOperand);
        args.putString(operationKey, operation);
        fragment.setArguments(args);
        return fragment;
    }

    public void setArguments(double firstOperand, double secondOperand, String operation) {
        Bundle args = getArguments();
        args.clear();
        Bundle bundle = new Bundle();
        bundle.putDouble(firstOperandKey, firstOperand);
        bundle.putDouble(secondOperandKey, secondOperand);
        bundle.putString(operationKey, operation);
        args.putAll(bundle);
    }

    public String getExpression() {
        String expression = "";
        double firstOperand = getFirstOperand();
        double secondOperand = getSecondOperand();
        if (firstOperand < 0)
            expression += "(" + firstOperand + ")";
        else
            expression += firstOperand;
        expression += " " + getOperation() + " ";
        if (secondOperand < 0)
            expression += "(" + secondOperand + ")";
        else
            expression += secondOperand;
        expression += " =";
        return expression;
    }


    public String getOperation() {
        return getArguments().getString(operationKey);
    }

    public double getFirstOperand() {
        return getArguments().getDouble(firstOperandKey);
    }

    public double getSecondOperand() {
        return getArguments().getDouble(secondOperandKey);
    }

    public double getResult() {
        double result = 0;
        double firstOperand = getFirstOperand();
        double secondOperand = getSecondOperand();
        switch (getOperation()) {
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                result = firstOperand / secondOperand;
                break;
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
        }
        return result;
    }
}
