package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.BarcodeScannerModel;

public class BarcodeScannerViewModel extends ViewModel {

    private String qrData;

    public void setQRData(String qr) { qrData = qr; }

    public LiveData<String> getResult() {
        BarcodeScannerModel bsModel = new BarcodeScannerModel(qrData);
        return bsModel.getResult();
    }

}
