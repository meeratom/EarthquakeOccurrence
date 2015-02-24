package com.anz.meera.earthquakeoccurrence;

import android.app.Application;
import android.net.http.AndroidHttpClient;
import android.test.ApplicationTestCase;

import org.apache.http.client.methods.HttpGet;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public boolean eRecordComp (EarthquakeRecord first, EarthquakeRecord second) {

        return first.getLatitude() == second.getLatitude() &&
                first.getLongitude() == second.getLongitude() &&
                first.getMagnitude() == second.getMagnitude();
    }

    public void test() throws Exception {

        EarthquakeRecord r1 = new EarthquakeRecord(-28.0614,-63.0770,6.7),
                r2 = new EarthquakeRecord(26.8756,140.2145,6.0),
                r3 = new EarthquakeRecord(72.9936,5.6513,6.2),
                r4 = new EarthquakeRecord(41.3861,142.0833,6.0),
                r5 = new EarthquakeRecord(39.5971,143.2421,6.0),
                r6 = new EarthquakeRecord(44.7998,11.1922,6.0),
                r7 = new EarthquakeRecord(-44.5940,-80.0735,6.2),
                r8 = new EarthquakeRecord(-5.5556,149.7103,6.0),
                r9 = new EarthquakeRecord(-5.5556,149.7103,6.0),
                r10 = new EarthquakeRecord(-17.8162,-69.7494,6.2),
                r11 = new EarthquakeRecord(14.4656,-92.9088,6.0),
                r12 = new EarthquakeRecord(14.4656,-92.9088,6.0);

        ArrayList<EarthquakeRecord> expected = new ArrayList<>(Arrays.asList(r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12));

        String URL = "http://www.seismi.org/api/eqs/2012/05?min_magnitude=6";
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("");
        HttpGet request = new HttpGet(URL);
        JSONResponseHandler responseHandler = new JSONResponseHandler();
        ArrayList<EarthquakeRecord> result = httpClient.execute(request, responseHandler);
        assertEquals(expected.size(),result.size());
        for (int i=0; i < expected.size(); i++)
            assertEquals(true, eRecordComp(expected.get(i), result.get(i)));
    }
}