package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.FindSeatModel;

public class FindSeatViewModel extends ViewModel {

    private final String TAG = "FindSeatViewModel";

    private MutableLiveData<String> seatAmount = new MutableLiveData<>();
    private MutableLiveData<String> building = new MutableLiveData<>();
    private MutableLiveData<String> floor = new MutableLiveData<>();

    public LiveData<String> getBuilding() { return building; }

    public void setSeatAmount(String seatAmount) {
        this.seatAmount.setValue(seatAmount);
    }

    public void setBuilding(String building) {
        this.building.setValue(building);
    }

    public void setFloor(String floor) {
        this.floor.setValue(floor);
    }

    public LiveData<String> getResult() {
        FindSeatModel findSeatModel = new FindSeatModel(seatAmount.getValue(), building.getValue(), floor.getValue());
        return findSeatModel.getResult();
    }

    public LiveData<List<String>> getBuildingResult() {
        FindSeatModel findSeatModel = new FindSeatModel("BUILDINGS", null);
        return findSeatModel.getResultList();
    }

    public LiveData<List<String>> getFloorResult(String buildingName) {
        FindSeatModel findSeatModel = new FindSeatModel("FLOORS", buildingName);
        return findSeatModel.getResultList();
    }
}
