package com.strang6.tinkofffintechschool;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OperationFragment extends Fragment implements View.OnClickListener {

    private OnOperationEnteredListener mListener;
    private String operation = null;

    public OperationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        Button multiplicationButton = (Button) view.findViewById(R.id.multiplicationButton);
        multiplicationButton.setOnClickListener(this);
        Button divisionButton = (Button) view.findViewById(R.id.divisionButton);
        divisionButton.setOnClickListener(this);
        Button additionButton = (Button) view.findViewById(R.id.additionButton);
        additionButton.setOnClickListener(this);
        Button subtractionButton = (Button) view.findViewById(R.id.subtractionButton);
        subtractionButton.setOnClickListener(this);
        if (savedInstanceState != null)
            operation = savedInstanceState.getString("operation");
        if (operation != null) {
            TextView textView = (TextView) view.findViewById(R.id.enteredOperationTextView);
            String operationText = getResources().getString(R.string.enteredOperation) + " " + operation;
            textView.setText(operationText);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnOperationEnteredListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnOperationEnteredListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.multiplicationButton:
                operation = "*";
                break;
            case R.id.divisionButton:
                operation = "/";
                break;
            case R.id.additionButton:
                operation = "+";
                break;
            case R.id.subtractionButton:
                operation = "-";
                break;
        }
        mListener.onOperationEntered(operation);
    }

    public interface OnOperationEnteredListener {
        void onOperationEntered(String operation);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("operation", operation);
    }


}
