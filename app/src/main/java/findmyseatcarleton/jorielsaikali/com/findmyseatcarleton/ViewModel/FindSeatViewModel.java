package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.FindSeatModel;

public class FindSeatViewModel extends ViewModel {

    private final String TAG = "FindSeatViewModel";

    private String seatAmount, building, floor;

    public void setSeatAmount(String seatAmount) { this.seatAmount = seatAmount; Log.i(TAG, this.seatAmount); }
    public void setBuilding(String building) { this.building = building; Log.i(TAG, this.building); }
    public void setFloor(String floor) { this.floor = floor; Log.i(TAG, this.floor); }

    public LiveData<List<String>> getResult() {
        //FindSeatModel findSeatModel = new FindSeatModel(seatAmount, building, floor);
        //return findSeatModel.getResult();
        return null;
    }

    public LiveData<List<String>> getBuildingResult() {
        FindSeatModel findSeatModel = new FindSeatModel("BUILDINGS", null);
        return findSeatModel.getResultList();
    }
}
