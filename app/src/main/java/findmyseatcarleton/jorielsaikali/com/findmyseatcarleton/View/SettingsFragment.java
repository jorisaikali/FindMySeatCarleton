package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        // ------------ Finding and setting RecyclerView ------------- //
        RecyclerView recyclerView = view.findViewById(R.id.settingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        // ----------------------------------------------------------- //

        // -------------- Creating and settings options --------------- //
        List<String> options = new ArrayList<>(Arrays.asList("Profile Settings", "Credits"));
        adapter = new SettingsAdapter(options);
        recyclerView.setAdapter(adapter);
        // ------------------------------------------------------------ //

        // ------------- ADAPTER OPTION LISTENER -------------- //
        adapter.setOnItemClickListener(new SettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String option) {
                // if option chosen was Profile Settings
                if (option.equals("Profile Settings")) {
                    ProfileSettingsFragment profileSettingsFragment = new ProfileSettingsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.homeLayout, profileSettingsFragment)
                            .addToBackStack(null)
                            .commit();
                }
                // if option chosen was Credits
                else if (option.equals("Credits")) {
                    CreditsFragment creditsFragment = new CreditsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.homeLayout, creditsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        // --------------------------------------------------- //

        return view;
    }
}
