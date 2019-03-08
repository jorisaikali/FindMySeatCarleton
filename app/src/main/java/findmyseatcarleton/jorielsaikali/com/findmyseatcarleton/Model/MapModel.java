package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapModel {
    private final String TAG = "MapModel";

    private GoogleMap googleMap;
    private List<LatLng> markerCoordinates;
    private LatLng buildingCoordinates;

    public MapModel(GoogleMap gm, String coordinatesString) {
        googleMap = gm;
        markerCoordinates = parseCoordinatesString(coordinatesString);
        addAllMarkers(markerCoordinates);
        shiftCamera(buildingCoordinates);
    }

    public GoogleMap getGoogleMap() { return googleMap; }
    public List<LatLng> getMarkerCoordinates() { return markerCoordinates; }

    private void shiftCamera(LatLng position) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 19.0f));
    }

    private void addAllMarkers(List<LatLng> markers) {
        for (int i = 0; i < markers.size(); i++) {
            Log.i(TAG, "Adding marker");
            googleMap.addMarker(new MarkerOptions().position(markers.get(i)));
        }
    }

    private List<LatLng> parseCoordinatesString(String coordinatesString) {
        // format coordinatesString will come in
        // [
        //      {"latitude":"45.382113","longitude":"-75.697416"},
        //      {"id":"1","status":"1","number_of_seats":"2","building_ID":"8","on_floor":"Level 1","latitude":"45.382220","longitude":"-75.697110"},
        //      {"id":"4","status":"1","number_of_seats":"2","building_ID":"8","on_floor":"Level 1","latitude":"45.382171","longitude":"-75.697103"},
        //      {"id":"7","status":"1","number_of_seats":"2","building_ID":"8","on_floor":"Level 1","latitude":"45.382132","longitude":"-75.697183"}
        // ]

        List<LatLng> coordinates = new ArrayList<>();

        // 1. Add '{"server_response":' to the beginning of coordinatesString and "}" to the end
        StringBuilder sb = new StringBuilder();
        sb.append("{\"server_response\":").append(coordinatesString).append("}");
        String newCoordinatesString = sb.toString();

        //Log.i(TAG, "JSON: " + newCoordinatesString);

        // 2. Use JSON classes to parse JSON into 'latitude' and 'longitude' variables
        try {
            JSONObject jsonObject = new JSONObject(newCoordinatesString);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            //Log.i(TAG, "jsonArray.length: " + jsonArray.length());

            int index = 0;
            while (index < jsonArray.length()) {
                //Log.i(TAG, "index: " + index);

                Double lat = 0.0, lng = 0.0;

                JSONObject jo = jsonArray.getJSONObject(index); // gets the first object in the array
                lat = Double.valueOf(jo.getString("latitude"));
                lng = Double.valueOf(jo.getString("longitude"));

                Log.i(TAG, "jo.toString(): " + jo.toString());

                // 3. Create new LatLng based on the latitude and longitude
                if (lat != 0.0 && lng != 0.0) {
                    LatLng marker = new LatLng(lat, lng);

                    if (index == 0) {
                        Log.i(TAG, "Building Coordinate");
                        buildingCoordinates = marker;
                    }
                    else {
                        // 4. Push to coordinates ArrayList
                        Log.i(TAG, "Table Coordinate");
                        coordinates.add(marker);
                    }
                }

                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 5. Return coordinates
        for (int i = 0; i < coordinates.size(); i++) {
            Log.i(TAG, "coordinates.get(" + i + "):" + coordinates.get(i).toString());
        }

        return coordinates;
    }
}
