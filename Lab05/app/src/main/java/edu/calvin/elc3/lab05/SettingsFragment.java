package edu.calvin.elc3.lab05;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/*
 * @author: Ethan Clark (elc3) and Russ Clousing (rlc32)
 * @version: 1.0
 * @date: 10/7/2016
 */

public class SettingsFragment extends PreferenceFragment {

    /*
     * onCreate() is called when this SettingsFragment.java class is called
     *      sets the Fragment for the SettingsActivity class
     *
     * @param: savedInstanceState
     * @return: none
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

}
