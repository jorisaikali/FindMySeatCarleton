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
    private Button testRegisterButton, testLoginButton, testUpdateButton, testFindButton, testAddEntryButton, testResetEntryButton, testSelectWinnerButton;
    private Button testFloorListButton, testBuildingListButton;
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
        testRegisterButton = view.findViewById(R.id.registerTestButton);
        testLoginButton = view.findViewById(R.id.loginTestButton);
        testUpdateButton = view.findViewById(R.id.updateTestButton);
        testFindButton = view.findViewById(R.id.findTestButton);
        testAddEntryButton = view.findViewById(R.id.addEntryTestButton);
        testResetEntryButton = view.findViewById(R.id.resetTestButton);
        testSelectWinnerButton = view.findViewById(R.id.selectWinnerTestButton);
        testFloorListButton = view.findViewById(R.id.floorListTestButton);
        testBuildingListButton = view.findViewById(R.id.buildingListTestButton);
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

        // -------------------- testing -------------------- //
        testRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"REGISTER",
                        "testUsername",
                        "test@example.com",
                        "7543AD7C883707E7834FC3E45C55F2C6",
                        "55F885EDF77C1B7208606E750193B4CB508713495E024EFB8447B559559883CD9CD6FDA56ACDD3BE6855BA425300FF788B198E8B5701B9B1A2B9396D102504AC"};
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

        testLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LoginModel lm = new LoginModel();
                try {
                    String[] results = lm.encrypt("password1");
                    Log.i(TAG, "salt: " + results[1]);
                    Log.i(TAG, "hash: " + results[0]);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }*/

                // DATABASE/SERVER TESTING
                String[] args = {"LOGIN",
                        "testUsername",
                        "55F885EDF77C1B7208606E750193B4CB508713495E024EFB8447B559559883CD9CD6FDA56ACDD3BE6855BA425300FF788B198E8B5701B9B1A2B9396D102504AC"};
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

        testUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"UPDATE", "1", "1"};
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

        testFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"FIND", "2", "8", "Level 1"};
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

        testAddEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"ADD ENTRY", "1"};
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

        testFloorListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"FLOOR LIST", "8"};
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

        testBuildingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {"BUILDING LIST"};
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
