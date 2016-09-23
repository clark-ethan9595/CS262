package edu.calvin.elc3.lab03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author: Ethan Clark (elc3@students.calvin.edu)
 * @version: Fall2016
 * @date: September 22, 2016
 */

public class PasswordProtect extends AppCompatActivity {

    /**
     * onCreate runs when PasswordProtect runs in Android Studio
     *
     * onCreate has function onClick inside that runs when the button is clicked
     *
     * @param savedInstanceState
     * @return NONE
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_protect);

        final ImageView Picture = (ImageView) findViewById(R.id.imageView);
        Picture.setVisibility(View.INVISIBLE);

        final TextView Incorrect = (TextView) findViewById(R.id.textView2);
        Incorrect.setVisibility(View.INVISIBLE);

        Button Password_Check = (Button) findViewById(R.id.button);
        Password_Check.setOnClickListener(new OnClickListener() {

            /**
             * onClick runs when the button is clicked
             *
             * onClick checks the password entered by the user against the default string password
             *      defined below (correct_pass)
             *
             * @param v
             * @return NONE
             */
            @Override
            public void onClick(View v) {
                String correct_pass = "CalvinFitness";
                EditText enter_pass = (EditText) findViewById(R.id.editText);
                if (enter_pass.getText().toString().equalsIgnoreCase(correct_pass)) {
                    Picture.setVisibility(View.VISIBLE);
                    Incorrect.setVisibility(View.INVISIBLE);
                } else {
                    Picture.setVisibility(View.INVISIBLE);
                    Incorrect.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
