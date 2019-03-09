package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Adapters.SettingsAdapter;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class SettingsFragment extends Fragment {

    private SettingsAdapter adapter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.settingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        List<String> options = new ArrayList<>(Arrays.asList("Profile Settings", "Credits"));
        adapter = new SettingsAdapter(options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String option) {
                if (option.equals("Profile Settings")) {
                    ProfileSettingsFragment profileSettingsFragment = new ProfileSettingsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.homeLayout, profileSettingsFragment)
                            .addToBackStack(null)
                            .commit();
                }
                else if (option.equals("Credits")) {
                    CreditsFragment creditsFragment = new CreditsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.homeLayout, creditsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }
}
