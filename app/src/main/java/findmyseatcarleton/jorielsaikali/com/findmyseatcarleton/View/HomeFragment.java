package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";

    private ImageButton settingsButton, profileButton;
    private ImageButton updateButton, findButton;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        // ---------------- Finding all components ----------------- //
        updateButton = view.findViewById(R.id.updateButton);
        findButton = view.findViewById(R.id.findButton);

        settingsButton = view.findViewById(R.id.settingsButton);
        profileButton = view.findViewById(R.id.profileButton);
        // --------------------------------------------------------- //

        // Setting background of bottom navigation bar buttons to transparent
        settingsButton.setBackgroundColor(Color.TRANSPARENT);
        profileButton.setBackgroundColor(Color.TRANSPARENT);

        // -------------- UPDATE BUTTON LISTENER ---------------- //
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BarcodeScannerActivity.class);
                startActivity(intent);
            }
        });
        // ------------------------------------------------------ //

        // -------------- FIND BUTTON LISTENER ---------------- //
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeatAmountFragment seatAmountFragment = new SeatAmountFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeLayout, seatAmountFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        // ---------------------------------------------------- //

        // ------------- PROFILE BUTTON LISTENER ------------ //
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeLayout, profileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        // -------------------------------------------------- //

        // ------------- SETTINGS BUTTON LISTENER ------------ //
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeLayout, settingsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        // --------------------------------------------------- //

        return view;
    }
}
