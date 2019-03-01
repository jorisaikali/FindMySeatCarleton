package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

public class Repository {

    private final String TAG = "Repository";

    private RemoteData rm = new RemoteData();
    private MutableLiveData<String> result = new MutableLiveData<>();

    public LiveData<String> getResult() {
        return result;
    }

    public LiveData<String> getData(String[] args) {
        result.setValue(getResponse(args));
        return getResult();
    }

    private String getResponse(String[] args) {
        return rm.getResult(args);
    }
}
