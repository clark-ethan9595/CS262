package edu.calvin.elc3.lab05;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/*
 * @author: Ethan Clark (elc3) and Russ Clousing (rlc32)
 * @version: 1.0
 * @date: 10/7/2016
 */

public class SettingsActivity extends AppCompatActivity {
    /*
     * onCreate is called when the settings item is clicked in the Menu
     *      sets the Settings Fragment as part of its layout
     *
     * @param: savedInstanceState
     * @return: NONE
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    /*
     * onCreateOptionsMenu removes the menu icon on the top of the screen
     *      forcing the user to use the Android back arrow to return to MainActivity
     *
     * @param: menu
     * @return: false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
