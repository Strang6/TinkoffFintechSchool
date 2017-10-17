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
        args.putDouble("firstOperand", firstOperand);
        args.putDouble("secondOperand", secondOperand);
        args.putString("operation", operation);
        fragment.setArguments(args);
        return fragment;
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
        return getArguments().getString("operation");
    }

    public double getFirstOperand() {
        return getArguments().getDouble("firstOperand");
    }

    public double getSecondOperand() {
        return getArguments().getDouble("secondOperand");
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
