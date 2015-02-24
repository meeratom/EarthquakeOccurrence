package com.anz.meera.earthquakeoccurrence;

import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private int yearSelected = 2009;
    private int monthSelected = 1;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Drop down menu for selecting month */
        Spinner month_spinner = (Spinner) findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> month_adapter = ArrayAdapter.createFromResource
                (this, R.array.months_array, android.R.layout.simple_spinner_item);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(month_adapter);
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                monthSelected = position;
                Log.i(TAG,"Month selected is "+ parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Log.i(TAG, "Month not selected");
            }
        });

        /*Drop down menu for selecting year */
        Spinner year_spinner = (Spinner) findViewById(R.id.year_spinner);
        final ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource
                (this, R.array.years_array, android.R.layout.simple_spinner_item);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(year_adapter);
        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                yearSelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
                Log.i(TAG, "Year selected is " + yearSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Log.i(TAG, "Year not selected");
            }

        });

        /*Calls execute on async task */
        final Button earthquakeData = (Button) findViewById(R.id.button);
        earthquakeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG,"Button Clicked");

                String url = "http://www.seismi.org/api/eqs/"+yearSelected+"/"+monthSelected+"?min_magnitude=6";
                earthquakeData.setClickable(false);
                new GetDataTask().execute(url);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class GetDataTask extends AsyncTask<String, Void, ArrayList<EarthquakeRecord>> {

        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");

        @Override
        protected ArrayList<EarthquakeRecord> doInBackground(String... params) {

            HttpGet request = new HttpGet(params[0]);
            JSONResponseHandler responseHandler = new JSONResponseHandler();

            try {
                /*Get earthquake data in JSON format and calls the responseHandler
                  to parse it into an ArrayList of EarthquakeRecords
                 */
                return httpClient.execute(request, responseHandler);

            }catch (ClientProtocolException e) {

                Log.i(TAG, "ClientProtocolException");

            }catch (IOException e) {

                Log.i(TAG,"IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute (ArrayList<EarthquakeRecord> result) {

            if (httpClient != null) {
                httpClient.close();
            }
            Button earthquakeData = (Button) findViewById(R.id.button);
            earthquakeData.setClickable(true);

            /*Once the ArrayList is ready, parcel it and pass it as Extra
              while starting MapsActivity
             */
            if (result != null) {

                Intent i = new Intent(MainActivity.this,MapsActivity.class);
                i.putParcelableArrayListExtra("EarthQuakeRecordList", result);
                startActivity(i);
            }

        }

    }
}
