package edu.calvin.elc3.lab05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

/*
 * @author: Ethan Clark (elc3) and Russ Clousing (rlc32)
 * @version: 1.0
 * @date: 9/27/2016
 */
public class MainActivity extends AppCompatActivity {

    /*
     * onCreate called when application starts up
     *
     * @param: savedInstanceState
     * @return: NONE
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * onCreateOptionsMenu creates the menu at the top of the page layout
     *
     * @param: menu
     * @return: true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    /*
     * onOptionsItemSelected performs actions if a menu item is selected
     *
     * @param item
     * @return true -> if About item is clicked or if Settings item is clicked
     *         super.onOptionsItemSelected(item) -> otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
