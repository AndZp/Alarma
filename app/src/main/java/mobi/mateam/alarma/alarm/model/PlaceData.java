package mobi.mateam.alarma.alarm.model;

import com.google.android.gms.location.places.Place;

/**
 * Created by Des63rus on 3/10/2017.
 *
 * Class for storing place date needed for us
 */

public class PlaceData {

    private String name;

    private String lon;

    private String lat;


    public PlaceData(Place place) {
        this.name = place.getName().toString();
        this.lon = String.valueOf(Double.valueOf(place.getLatLng().longitude));
        this.lat = String.valueOf(Double.valueOf(place.getLatLng().latitude));
    }

    public String getName() {
        return name;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }
}
