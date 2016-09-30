package edu.calvin.elc3.lab04;

import android.os.Bundle;
import android.view.Menu;

/**
 * @author: Ethan Clark (elc3)
 * @version: 1.0
 * @date: 9/27/2016
 */
public class AboutActivity extends MainActivity {

    /**
     * onCreate is called when the application starts up
     *
     * @param: savedInstanceState
     * @return: NONE
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    /**
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
