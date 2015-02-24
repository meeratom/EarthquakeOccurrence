package com.anz.meera.earthquakeoccurrence;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;


/*Handle the httpresponse */

public class JSONResponseHandler implements ResponseHandler<ArrayList<EarthquakeRecord>> {

    public static final String TAG = "JSONResponseHandler";

    public ArrayList<EarthquakeRecord> handleResponse(HttpResponse response)
            throws ClientProtocolException, IOException {

        ArrayList<EarthquakeRecord> result = new ArrayList<>();
        /*Parses the Httpresponse to String */
        String JSONResponse = new BasicResponseHandler().handleResponse(response);


        try {

            /*Parse the string to EarthquakeRecord ArrayList*/
            JSONObject object = (JSONObject) new JSONTokener(JSONResponse).nextValue();
            JSONArray earthquakeDetails = object.getJSONArray("earthquakes");

            for (int i = 0; i < earthquakeDetails.length(); i++) {

                JSONObject earthquakeInstance = earthquakeDetails.getJSONObject(i);
                result.add(new EarthquakeRecord(earthquakeInstance.getDouble("lat"),
                        earthquakeInstance.getDouble("lon"),
                        earthquakeInstance.getDouble("magnitude")));
            }

        } catch (JSONException e) {

            Log.i(TAG, "JSONException");

        } catch (NullPointerException e) {

            Log.i(TAG, "NullPointerException");
        }

        return result;
    }

}

