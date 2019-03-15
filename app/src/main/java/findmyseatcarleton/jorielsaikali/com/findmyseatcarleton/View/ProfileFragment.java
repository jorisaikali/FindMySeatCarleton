package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private TextView usernameTextView, emailTextView, currentEntriesTextView, totalEntriesTextView;
    private Button logoutButton;
    private Button resetEntriesButton, selectWinnerButton;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        // ----------- Finding all components ----------- //
        usernameTextView = view.findViewById(R.id.usernameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        currentEntriesTextView = view.findViewById(R.id.currentEntriesTextView);
        totalEntriesTextView = view.findViewById(R.id.totalEntriesTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        resetEntriesButton = view.findViewById(R.id.resetEntriesButton);
        selectWinnerButton = view.findViewById(R.id.selectWinnerButton);
        // ---------------------------------------------- //

        // ----------- If user is admin, display admin-only buttons ---------- //
        if (MainActivity.username.equals("admin")) {
            resetEntriesButton.setVisibility(View.VISIBLE);
            selectWinnerButton.setVisibility(View.VISIBLE);
        }
        else {
            resetEntriesButton.setVisibility(View.GONE);
            selectWinnerButton.setVisibility(View.GONE);
        }
        // ------------------------------------------------------------------- //

        // ------------------ LOGOUT BUTTON LISTENER -------------------- //
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.username = ""; // set username the empty

                // send user back to login screen without allowing back button to bring them back
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        // -------------------------------------------------------------- //

        // ------------------ RESET ENTRIES BUTTON LISTENER -------------------- //
        resetEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getResultAdmin(0).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // --------------------------------------------------------------------- //

        // ------------------ SELECT WINNER BUTTON LISTENER -------------------- //
        selectWinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getResultAdmin(1).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // --------------------------------------------------------------------- //

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);

        mViewModel.setUsername(MainActivity.username); // set username so ProfileViewModel can use it

        // ----------------- Observe result LiveData from ProfileViewModel --------------- //
        mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // if result was a success
                if (s.equals("SUCCESS")) {

                    // ------------ Parsing entries ------------- //
                    String parsedCurrentEntries = parseEntries(currentEntriesTextView.getText().toString(), mViewModel.getCurrentEntries());
                    String parsedTotalEntries = parseEntries(totalEntriesTextView.getText().toString(), mViewModel.getTotalEntries());
                    // ------------------------------------------ //

                    // ---------- Display texts to user ---------- //
                    usernameTextView.setText(MainActivity.username);
                    emailTextView.setText(mViewModel.getEmail());
                    currentEntriesTextView.setText(parsedCurrentEntries);
                    totalEntriesTextView.setText(parsedTotalEntries);
                    // ------------------------------------------- //
                }
                // if result was not a success, display an error message
                else {
                    Toast.makeText(getActivity(), "Error has occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // ------------------------------------------------------------------------------ //
    }

    private String parseEntries(String entries, String entriesAmount) {
        // entries are formatted as "Current Entries: X" and "Total Entries: Y"
        // we have to get the index of the : so we can replace the number after it
        int indexOfColon = entries.indexOf(":");

        StringBuilder sb = new StringBuilder();
        sb.append(entries.substring(0, indexOfColon+1));
        sb.append(" ");
        sb.append(entriesAmount);

        return sb.toString();
    }
}
