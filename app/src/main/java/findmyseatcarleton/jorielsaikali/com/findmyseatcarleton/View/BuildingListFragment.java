package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingListFragment extends Fragment {


    public BuildingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_building_list, container, false);
    }

}
