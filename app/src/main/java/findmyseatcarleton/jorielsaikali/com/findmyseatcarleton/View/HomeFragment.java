package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database.Repository;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Model.LoginModel;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.HomeViewModel;

public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";

    private HomeViewModel mViewModel;

    private ImageButton settingsButton, homeButton, profileButton;
    private ImageButton updateButton, findButton;

    // -------------------- Testing ------------------- //
    private Button testResetEntryButton, testSelectWinnerButton;
    // ------------------------------------------------ //

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        updateButton = view.findViewById(R.id.updateButton);
        findButton = view.findViewById(R.id.findButton);

        settingsButton = view.findViewById(R.id.settingsButton);
        homeButton = view.findViewById(R.id.homeButton);
        profileButton = view.findViewById(R.id.profileButton);

        // -------------------- testing -------------------- //
        testResetEntryButton = view.findViewById(R.id.resetTestButton);
        testSelectWinnerButton = view.findViewById(R.id.selectWinnerTestButton);
        // ------------------------------------------------- //

        settingsButton.setBackgroundColor(Color.TRANSPARENT);
        homeButton.setBackgroundColor(Color.TRANSPARENT);
        profileButton.setBackgroundColor(Color.TRANSPARENT);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BarcodeScannerActivity.class);
                startActivity(intent);
            }
        });

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

        // -------------------- testing -------------------- //
        testResetEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"RESET ENTRY"};
                Repository r = new Repository(args);
                r.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.i(TAG, "s: " + s);
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        testSelectWinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"SELECT WINNER"};
                Repository r = new Repository(args);
                r.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.i(TAG, "s: " + s);
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // ------------------------------------------------- //

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
