package edu.calvin.elc3.lab06;

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

/**
 * Reads openweathermap's RESTful API for weather forecasts.
 * The code is based on Deitel's WeatherViewer (Chapter 17), simplified based on Murach's NewsReader (Chapter 10).
 * <p>
 * for CS 262, lab 6
 *
 * @author kvlinden
 *      extended by Ethan Clark (elc3)
 * @version summer, 2016
 *
 * Answers to questions:
 *  What does the application do for invalid cities?
 *      The application seems to take input for a lot of invalid cities, maybe it is converting the input somehow and still
 *      generating weather information for a city. Otherwise, sometimes it does recognize an invalid city and generate
 *      a toast saying "failed to connect to service...".
 *
 *  What is the API key? What does it do?
 *      The API key is 8301442678d36413b4e3a04d016deee7.
 *      The API key identifies the calling program, its developer, or its user to the Web Site.
 *
 *  What does the full JSON response look like?
 *      The full JSON response is MASSIVE.
 *      {"city":{"id":4994358,"name":"Grand Rapids","coord":{"lon":-85.668091,"lat":42.96336},"country":"US","population":0},
 *      "cod":"200","message":0.3425,"cnt":7,"list":[{"dt":1476464400,"temp":{"day":59.47,"min":48.16,"max":60.98,"night":48.
 *      16,"eve":54.39,"morn":59.47},"pressure":1010.11,"humidity":68,"weather":[{"id":800,"main":"Clear","description":
 *      "clear sky","icon":"01d"}],"speed":11.9,"deg":190,"clouds":0},{"dt":1476550800,"temp":{"day":65.88,"min":48.29,"max"
 *      :70.9,"night":65.95,"eve":68.14,"morn":48.29},"pressure":1000.92,"humidity":80,"weather":[{"id":501,"main":"Rain",
 *      "description":"moderate rain","icon":"10d"}],"speed":16.8,"deg":209,"clouds":92,"rain":6.68},{"dt":1476637200,"temp":
 *      {"day":68.4,"min":56.08,"max":68.59,"night":61.09,"eve":56.77,"morn":60.58},"pressure":1001.19,"humidity":88,"weather"
 *      :[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"speed":4.74,"deg":248,"clouds":36,"rain":2.4},
 *      {"dt":1476723600,"temp":{"day":65.52,"min":56.26,"max":66.99,"night":56.26,"eve":59.88,"morn":66.99},"pressure":992.7,
 *      "humidity":0,"weather":[{"id":501,"main":"Rain","description":"moderate rain","icon":"10d"}],"speed":14.52,"deg":279,
 *      "clouds":0,"rain":9.07},{"dt":1476810000,"temp":{"day":67.77,"min":56.52,"max":69.22,"night":59.11,"eve":69.22,"morn"
 *      :56.52},"pressure":988.47,"humidity":0,"weather":[{"id":501,"main":"Rain","description":"moderate rain","icon":"10d"}],
 *      "speed":10.07,"deg":141,"clouds":37,"rain":9.97},{"dt":1476896400,"temp":{"day":58.05,"min":51.31,"max":58.05,"night"
 *      :51.31,"eve":56.12,"morn":56.43},"pressure":990.01,"humidity":0,"weather":[{"id":501,"main":"Rain","description":
 *      "moderate rain","icon":"10d"}],"speed":15.73,"deg":279,"clouds":51,"rain":4.8},{"dt":1476982800,"temp":{"day":52.83,
 *      "min":41.67,"max":52.83,"night":41.67,"eve":48.85,"morn":48.6},"pressure":1004.63,"humidity":0,"weather":[{"id":500,
 *      "main":"Rain","description":"light rain","icon":"10d"}],"speed":8.01,"deg":20,"clouds":58,"rain":0.92}]}
 *
 *  What does the system do with JSON data?
 *      The system looks for the keywords of "dt", there should be seven of them for the entire week, and then looks for
 *      the appropriate keywords inside each of those lists, in this case "description", "min", and "max", and pulls the values
 *      associated with each of those keywords.
 *
 *  What is the Weather class designed to do?
 *      The Weather class is designed to model a Weather object, convert the huge long date number into the actual day of
 *      the week, and also have accessors for each of the instance variables.
 *
 */
public class MainActivity extends AppCompatActivity {

    private EditText cityText;
    private Button fetchButton;

    private List<Weather> weatherList = new ArrayList<>();
    private ListView itemsListView;

    /* This formater can be used as follows to format temperatures for display.
     *     numberFormat.format(SOME_DOUBLE_VALUE)
     */
    //private NumberFormat numberFormat = NumberFormat.getInstance();

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = (EditText) findViewById(R.id.cityText);
        fetchButton = (Button) findViewById(R.id.fetchButton);
        itemsListView = (ListView) findViewById(R.id.weatherListView);

        // See comments on this formatter above.
        //numberFormat.setMaximumFractionDigits(0);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissKeyboard(cityText);
                new GetWeatherTask().execute(createURL(cityText.getText().toString()));
            }
        });
    }

    /**
     * Formats a URL for the webservice specified in the string resources.
     *
     * @param city the target city
     * @return URL formatted for openweathermap.com
     */
    private URL createURL(String city) {
        try {
            String urlString = getString(R.string.web_service_url) +
                    URLEncoder.encode(city, "UTF-8") +
                    "&units=" + getString(R.string.openweather_units) +
                    "&cnt=" + getString(R.string.openweather_count) +
                    "&APPID=" + getString(R.string.openweather_api_key);
            return new URL(urlString);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /**
     * Deitel's method for programmatically dismissing the keyboard.
     *
     * @param view the TextView currently being edited
     */
    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Inner class for GETing the current weather data from openweathermap.org asynchronously
     */
    private class GetWeatherTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... params) {
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
                    return new JSONObject(result.toString());
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

        @Override
        protected void onPostExecute(JSONObject weather) {
            if (weather != null) {
                Log.d(TAG, weather.toString());
                convertJSONtoArrayList(weather);
                MainActivity.this.updateDisplay();
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Converts the JSON weather forecast data to an arraylist suitable for a listview adapter
     *
     * @param forecast
     */
    private void convertJSONtoArrayList(JSONObject forecast) {
        weatherList.clear(); // clear old weather data
        try {
            JSONArray list = forecast.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject day = list.getJSONObject(i);
                JSONObject temperatures = day.getJSONObject("temp");
                String low = Integer.toString(temperatures.getInt("min"));
                String high = Integer.toString(temperatures.getInt("max"));
                JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                weatherList.add(new Weather(
                        day.getLong("dt"),
                        weather.getString("description"), low, high));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refresh the weather data on the forecast ListView through a simple adapter
     */
    private void updateDisplay() {
        if (weatherList == null) {
            Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (Weather item : weatherList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("day", item.getDay());
            map.put("description", item.getSummary());
            map.put("low", item.getLow());
            map.put("high", item.getHigh());
            data.add(map);
        }

        int resource = R.layout.weather_item;
        String[] from = {"day", "description", "low", "high"};
        int[] to = {R.id.dayTextView, R.id.summaryTextView, R.id.LowTempView, R.id.HighTempView};

        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);
    }

}
