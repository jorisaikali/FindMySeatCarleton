package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.BarcodeScannerModel;

public class BarcodeScannerViewModel extends ViewModel {

    private String qrData;

    public List<String> getAlreadySeenQRCodes() {
        BarcodeScannerModel bsModel = new BarcodeScannerModel();
        return bsModel.getAlreadySeenQRCodes();
    }

    public void setAlreadySeenQRCodes(List<String> alreadySeenQRCodes) {
        BarcodeScannerModel bsModel = new BarcodeScannerModel();
        bsModel.setAlreadySeenQRCodes(alreadySeenQRCodes);
    }

    public void setQRData(String qr) { qrData = qr; }

    public LiveData<String> getResult() {
        BarcodeScannerModel bsModel = new BarcodeScannerModel(qrData);
        return bsModel.getResult();
    }

}
