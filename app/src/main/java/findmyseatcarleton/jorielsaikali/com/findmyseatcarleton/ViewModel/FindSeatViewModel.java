package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.FindSeatModel;

public class FindSeatViewModel extends ViewModel {

    private String seatAmount, building, floor;

    public void setSeatAmount(String seatAmount) { this.seatAmount = seatAmount; }
    public void setBuilding(String building) { this.building = building; }
    public void setFloor(String floor) { this.floor = floor; }

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
