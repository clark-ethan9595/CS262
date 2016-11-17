package edu.calvin.elc3.lab02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

/**
 * @author Ethan Clark elc3@students.calvin.edu
 * @author Russel Clousing rlc32@students.calvin.edu
 * @version 1.0
 * September 16, 2016
 */

public class DoubleCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_calculator);
    }

    /**
     * Function to double the inputted value of the user
     *
     * @param variable mainView
     */
    public void doubleValue(View mainView){
        EditText editText = (EditText)findViewById(R.id.editText);
        Integer startVal = Integer.parseInt(editText.getText().toString());
        TextView display = (TextView)findViewById(R.id.textView);
        display.setText(Integer.toString(startVal * 2));
    }
}

