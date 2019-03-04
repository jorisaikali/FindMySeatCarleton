package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Adapters.BuildingListAdapter;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.FindSeatViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingListFragment extends Fragment {

    private final String TAG = "BuildingListFragment";

    private FindSeatViewModel mViewModel;

    public static BuildingListFragment newInstance() {
        return new BuildingListFragment();
    }

    public BuildingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_building_list, container, false);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FindSeatViewModel.class);

        RecyclerView recyclerView = getView().findViewById(R.id.buildingListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final BuildingListAdapter adapter = new BuildingListAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel.getBuildingResult().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                for (int i = 0; i < strings.size(); i++) {
                    Log.i(TAG, "resultList.get(i): " + strings.get(i));
                }

                adapter.setBuildings(strings);
            }
        });
    }
}
