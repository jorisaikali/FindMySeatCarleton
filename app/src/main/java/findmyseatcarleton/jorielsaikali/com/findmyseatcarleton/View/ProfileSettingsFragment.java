package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.ProfileSettingsViewModel;

public class ProfileSettingsFragment extends Fragment {

    private final String TAG = "ProfileSettingsFragment";

    private ProfileSettingsViewModel mViewModel;

    private EditText oldPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private EditText newEmailEditText, confirmEmailEditText;
    private TextView oldPasswordErrorText, newPasswordErrorText, confirmPasswordErrorText, newEmailErrorText, confirmEmailErrorText;
    private Button changePasswordButton, changeEmailButton;

    public static ProfileSettingsFragment newInstance() {
        return new ProfileSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_settings_fragment, container, false);

        // ------------------ Finding all components -------------------- //
        oldPasswordEditText = view.findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        newEmailEditText = view.findViewById(R.id.newEmailEditText);
        confirmEmailEditText = view.findViewById(R.id.confirmEmailEditText);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        changeEmailButton = view.findViewById(R.id.changeEmailButton);

        oldPasswordErrorText = view.findViewById(R.id.oldPasswordErrorText);
        newPasswordErrorText = view.findViewById(R.id.newPasswordErrorText);
        confirmPasswordErrorText = view.findViewById(R.id.confirmPasswordErrorText);
        newEmailErrorText = view.findViewById(R.id.newEmailErrorText);
        confirmEmailErrorText = view.findViewById(R.id.confirmEmailErrorText);
        // -------------------------------------------------------------- //

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProfileSettingsViewModel.class);

        // --------------- CHANGE PASSWORD LISTENER ------------------ //
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Clear all error texts
                clearAllErrorTexts();

                // 2. Get user input
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // 3. Set arguments for ProfileSettingsViewModel to use
                String[] args = {MainActivity.username, oldPassword, newPassword, confirmPassword};
                mViewModel.setArgs(args);

                // 4. Observe when result in ProfileSettingsViewModel changes
                mViewModel.getResult("PASSWORD").observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        // if result is successful, display to the user success and empty fields
                        if (s.equals("SUCCESS")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            emptyFields();
                        }
                        // if result is unsuccessful, display error messages to user
                        else {
                            String[] errors = s.split(";"); // each error is separated by a ;
                            displayPasswordErrors(errors);
                        }
                    }
                });
            }
        });
        // ----------------------------------------------------------- //

        // ----------------- CHANGE EMAIL LISTENER ------------------ //
        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Clear all error texts
                clearAllErrorTexts();

                // 2. Get user input
                String newEmail = newEmailEditText.getText().toString();
                String confirmEmail = confirmEmailEditText.getText().toString();

                // 3. Set arguments for ProfileSettingsViewModel to use
                String[] args = {MainActivity.username, newEmail, confirmEmail};
                mViewModel.setArgs(args);

                // 4. Observe when result in ProfileSettingsViewModel changes
                mViewModel.getResult("EMAIL").observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        // if result is successful, display to the user success and empty fields
                        if (s.equals("SUCCESS")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            emptyFields();
                        }
                        // if result is unsuccessful, display error messages to user
                        else {
                            String[] errors = s.split(";"); // each error is separated by a ;
                            displayEmailErrors(errors);
                        }
                    }
                });
            }
        });
        // --------------------------------------------------------- //
    }

    private void emptyFields() {
        oldPasswordEditText.setText("");
        newPasswordEditText.setText("");
        confirmPasswordEditText.setText("");
        newEmailEditText.setText("");
        confirmEmailEditText.setText("");
    }

    private void clearAllErrorTexts() {
        oldPasswordErrorText.setVisibility(View.GONE);
        newPasswordErrorText.setVisibility(View.GONE);
        confirmPasswordErrorText.setVisibility(View.GONE);
        newEmailErrorText.setVisibility(View.GONE);
        confirmEmailErrorText.setVisibility(View.GONE);
    }

    private void displayPasswordErrors(String[] errors) {
        // Iterate through each error in errors and set and display corresponding field
        for (String error: errors) {
            switch (error) {
                case "All fields are required":
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    break;
                case "Old password is not correct":
                    oldPasswordErrorText.setText(getResources().getString(R.string.old_password_no_match_error));
                    oldPasswordErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Passwords do not match":
                    confirmPasswordErrorText.setText(getResources().getString(R.string.password_not_match_error));
                    confirmPasswordErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Password must be between 5-25 characters":
                    newPasswordErrorText.setText(getResources().getString(R.string.password_length_error));
                    newPasswordErrorText.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void displayEmailErrors(String[] errors) {
        // Iterate through each error in errors and set and display corresponding field
        for (String error: errors) {
            switch (error) {
                case "All fields are required":
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    break;
                case "FAILED CHANGE PROFILE: Error occurred while updating email":
                    newEmailErrorText.setText(getResources().getString(R.string.email_already_taken_error));
                    newEmailErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Emails do not match":
                    confirmEmailErrorText.setText(getResources().getString(R.string.emails_not_match_error));
                    confirmEmailErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Email is not valid":
                    newEmailErrorText.setText(getResources().getString(R.string.email_not_valid_error));
                    newEmailErrorText.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
