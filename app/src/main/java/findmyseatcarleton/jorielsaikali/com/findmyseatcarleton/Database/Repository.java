package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

public class Repository {

    private final String TAG = "Repository";

    private LiveData<String> result;

    public Repository(String[] args) {
        RemoteData rm = new RemoteData();
        result = rm.getResult(args);
    }

    public LiveData<String> getResult() {
        return result;
    }
}
