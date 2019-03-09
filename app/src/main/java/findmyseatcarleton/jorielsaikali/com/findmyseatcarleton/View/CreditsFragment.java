package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class CreditsFragment extends Fragment {
    public CreditsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credits, container, false);
    }
}
