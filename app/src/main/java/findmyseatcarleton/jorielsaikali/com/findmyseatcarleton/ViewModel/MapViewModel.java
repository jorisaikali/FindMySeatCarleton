package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.MapModel;

public class MapViewModel extends ViewModel {
    private MapModel mapModel;
    private GoogleMap googleMap;
    private List<LatLng> markerCoordinates = new ArrayList<>();

    public GoogleMap getGoogleMap() { return googleMap; }
    public List<LatLng> getMarkerCoordinates() { return markerCoordinates; }

    public void setData(GoogleMap gm, String coordinatesString) {
        mapModel = new MapModel(gm, coordinatesString);
        markerCoordinates = mapModel.getMarkerCoordinates();
        googleMap = mapModel.getGoogleMap();
    }

}
