package com.strang6.tinkofffintechschool;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OperandFragment extends Fragment implements View.OnClickListener {

    OnOperandEnteredListener mListener;

    public OperandFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operand, container, false);
        Button okButton = (Button) view.findViewById(R.id.okButton);
        okButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnOperandEnteredListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnOperandEnteredListener");
        }
    }

    @Override
    public void onClick(View view) {
        EditText operandEditText = (EditText) getView().findViewById(R.id.operandEditText);
        String operandText = operandEditText.getText().toString();
        if (!operandText.isEmpty()) {
            double operand = Double.parseDouble(operandText);
            mListener.onOperandEntered(operand);
        } else {
            Toast.makeText(getContext(), "Введите операнд", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnOperandEnteredListener {
        void onOperandEntered(double operand);
    }

}
