package edu.calvin.elc3.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author      Ethan Clark (elc3)
 * @version     1.0
 * @date        9/27/2016
 */
public class SimpleCalculator extends AppCompatActivity {

    // Declare private variables for the class to use
    private SharedPreferences savedValues;
    private int Spinner_Index;
    private int val_one;
    private int val_two;
    private Spinner operator_option;

    /**
     * onCreate is called when the application starts up
     *
     * Contains a method for when the user clicks the calculate button
     *
     * @param: savedInstanceState
     * @return: NONE
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculator);

        // Set a savedValues to get used by the overriden onPause and onResume methods
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        // Two EditText fields
        final EditText value_one = (EditText) findViewById(R.id.editText);
        final EditText value_two = (EditText) findViewById(R.id.editText3);

        // Result TextView field
        final TextView final_val = (TextView) findViewById(R.id.textView4);

        // Declaring the spinner and setting the options for the dropdown menu
        operator_option = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operator_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        operator_option.setAdapter(adapter);

        Button calculate_button = (Button) findViewById(R.id.button);

        /**
         * setOnClickListener "listens" for the button to be clicked
         *
         * @param: OnClickListener
         * @return: NONE
         */
        calculate_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convert the EditText fields to integers
                val_one = Integer.parseInt(value_one.getText().toString());
                val_two = Integer.parseInt(value_two.getText().toString());

                // Get the current selected operator
                int current_op = operator_option.getSelectedItemPosition();

                // Determine which of the operators was selected by using their index position
                if (current_op == 0) {
                    final_val.setText(Integer.toString(val_one + val_two));
                }
                else if (current_op == 1) {
                    final_val.setText(Integer.toString(val_one - val_two));
                }
                else if (current_op == 2) {
                    final_val.setText(Integer.toString(val_one * val_two));
                }
                else if (current_op == 3) {
                    final_val.setText(Integer.toString(val_one / val_two));
                }
            }
        });
    }

    /**
     * Override method for the OnPause() method
     *
     * Saves the values in the two EditText fields and the currently selected operator
     *
     * @param: NONE
     * @return: NONE
     */
    @Override
    public void onPause() {
        Editor editor = savedValues.edit();
        editor.putInt("Value One", val_one);
        editor.putInt("Value Two", val_two);
        editor.putInt("Operator", operator_option.getSelectedItemPosition());
        editor.commit();

        super.onPause();
    }

    /**
     * Override method for the OnResume() method
     *
     * Sets the values in the two EditText fields and the selected operator
     *
     * @param: NONE
     * @return: NONE
     */
    @Override
    public void onResume() {
        super.onResume();

        val_one = savedValues.getInt("Value One", val_one);
        val_two = savedValues.getInt("Value Two", val_two);
        operator_option.setSelection(savedValues.getInt("Operator", 0));
    }
}

