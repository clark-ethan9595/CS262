package edu.calvin.elc3.homework2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;

/*
 * MainActivity does all of the bulk of the work for this Homework2
 *
 * Reads in a JSONArray of player objects from the course server and displays them appropriately
 *      if the EditText field is blank.
 * Reads in one JSONObject player from the course server and displays it appropriately
 *      if the EditText contains a positive integer.
 *
 * @author: Ethan Clark (elc3)
 * @version: 1.0
 * @date: October 19, 2016
 */

public class MainActivity extends AppCompatActivity {

    // Declare all the necessary private variables for this MainActivity
    private EditText playerText;
    private Button fetchButton;
    private List<Player> playerList = new ArrayList<>();
    private ListView itemsListView;
    private static String TAG = "MainActivity";
    private boolean determine;

    /*
     * onCreate() is called when the application starts up
     *
     * @param: savedInstanceState
     * @return: none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerText = (EditText) findViewById(R.id.playerText);
        fetchButton = (Button) findViewById(R.id.fetchButton);
        itemsListView = (ListView) findViewById(R.id.playerListView);

        // Listens for the button on the MainActivity to be pushed
        //      Dismisses the keyboard and then calls GetPlayersTask()
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(playerText);
                new GetPlayersTask().execute(createURL(playerText.getText().toString()));
            }
        });
    }

    /*
     * Deitel's method for programmatically dismissing the keyboard.
     *
     * @param view the TextView currently being edited
     *
     * Taken from Lab06
     */
    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param player the target player
     * @return URL formatted for http://cs262.cs.calvin.edu:8089/monopoly/players/
     *
     * Altered from Lab06
     */
    private URL createURL(String player) {
        try {
            String urlString;

            // If the EditText field was blank...
            if (player.equals("")) {
                determine = true;
                urlString = getString(R.string.web_service_url);
            }

            // Else take in the postive integer the user inputs...
            else {
                determine = false;
                urlString = getString(R.string.web_service_url_2) + URLEncoder.encode(player, "UTF-8");
            }

            return new URL(urlString);

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /*
     * GetPlayersTask class established connection with the web server,
     *      gets the JSON information from the server,
     *      and then calls the functions to update the display.
     */
    private class GetPlayersTask extends AsyncTask<URL, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(URL... params) {
            HttpURLConnection connection = null;
            StringBuilder result = new StringBuilder();
            try {
                connection = (HttpURLConnection) params[0].openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    if (determine) {
                        return new JSONArray(result.toString());
                    } else {
                        return new JSONArray("[" + result.toString() + "]");
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;
        }

        // Calls the necessary methods to update the display accordingly
        @Override
        protected void onPostExecute(JSONArray player) {
            if (player != null) {

                // Helpful to determine what the JSON format looks like (i.e. what the keys are)
                //Log.d(TAG, player.toString());

                convertJSONtoArrayList(player);
                MainActivity.this.updateDisplay();

            } else {
                Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * Converts the JSON player data to an arraylist suitable for a listview adapter
     *
     * @param player_obj
     *
     * Altered from Lab06
     */
    private void convertJSONtoArrayList(JSONArray player_obj) {

        // clear old player data
        playerList.clear();

        try {
            for (int i = 0; i < player_obj.length(); i++) {
                JSONObject player = player_obj.getJSONObject(i);
                String ID = Integer.toString(player.getInt("id"));
                String email = player.getString("emailaddress");
                String name;
                try {
                    name = player.getString("name");
                } catch(JSONException e) {
                    name = "No name given";
                }
                playerList.add(new Player(name, email, ID));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * Refresh the player data on the player ListView through a simple adapter
     *
     * Altered from Lab06
     */
    private void updateDisplay() {

        // Generate toast if playerList is empty...
        if (playerList == null) {
            Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }

        // Update ArrayList with the necessary elements
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (Player item : playerList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", item.getID_num());
            map.put("name", item.getName());
            map.put("email", item.getEmail_address());
            data.add(map);
        }

        int resource = R.layout.player_item;
        String[] from = {"id", "name", "email"};
        int[] to = {R.id.ID_textView, R.id.name_textView, R.id.email_textView};

        // Add the elements to the adapter and set the adapter in the itemsListView
        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);
        Log.d(TAG, "Display should be updated");
    }
}
