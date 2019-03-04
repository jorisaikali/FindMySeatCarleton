package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Adapters.FloorListAdapter;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.FindSeatViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FloorListFragment extends Fragment {

    private final String TAG = "FloorListFragment";

    private FindSeatViewModel mViewModel;

    public static FloorListFragment newInstance() {
        return new FloorListFragment();
    }

    public FloorListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_floor_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FindSeatViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i(TAG, "FloorListFragment ONSTART");

        RecyclerView recyclerView = getView().findViewById(R.id.floorListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final FloorListAdapter adapter = new FloorListAdapter();
        recyclerView.setAdapter(adapter);

        String buildingName = mViewModel.getBuilding();
        Log.i(TAG, "buildingName: " + buildingName);

        mViewModel.getFloorResult().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                for (int i = 0; i < strings.size(); i++) {
                    Log.i(TAG, strings.get(i));
                }

                adapter.setFloors(strings);
            }
        });

        adapter.setOnItemClickListener(new FloorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String floor) {
                mViewModel.setFloor(floor);

                mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        intent.putExtra("coordinatesString", s);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
