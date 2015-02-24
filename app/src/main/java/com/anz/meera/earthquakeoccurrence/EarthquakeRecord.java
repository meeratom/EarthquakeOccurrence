package com.anz.meera.earthquakeoccurrence;

import android.os.Parcel;
import android.os.Parcelable;

/*To store each earthquake instance data.
  Made it Parcelable, so that it can be passed on while starting MapsActivity
 */
public class EarthquakeRecord implements Parcelable {

    private double latitude, longitude, magnitude;

    protected EarthquakeRecord (double latitude, double longitude, double magnitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.magnitude = magnitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(magnitude);
    }

    public static final Creator<EarthquakeRecord> CREATOR
            = new Creator<EarthquakeRecord>() {
        public EarthquakeRecord createFromParcel(Parcel in) {

            return new EarthquakeRecord(in.readDouble(),
                    in.readDouble(), in.readDouble());

        }

        public EarthquakeRecord[] newArray(int size) {
            return new EarthquakeRecord[size];
        }
    };

}
