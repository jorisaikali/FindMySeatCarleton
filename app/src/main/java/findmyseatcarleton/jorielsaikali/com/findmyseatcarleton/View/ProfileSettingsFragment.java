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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProfileSettingsViewModel.class);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllErrorTexts();

                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                String[] args = {MainActivity.username, oldPassword, newPassword, confirmPassword};
                mViewModel.setArgs(args);

                mViewModel.getResult("PASSWORD").observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.i(TAG, "s: " + s);

                        if (s.equals("SUCCESS")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            emptyFields();
                        }
                        else {
                            String[] errors = s.split(";");

                            for (int i = 0; i < errors.length; i++) {
                                if (errors[i].equals("All fields are required")) {
                                    Toast.makeText(getActivity(), errors[i], Toast.LENGTH_SHORT).show();
                                }
                                else if (errors[i].equals("Old password is not correct")) {
                                    oldPasswordErrorText.setText(getResources().getString(R.string.old_password_no_match_error));
                                    oldPasswordErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Passwords do not match")) {
                                    confirmPasswordErrorText.setText(getResources().getString(R.string.password_not_match_error));
                                    confirmPasswordErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Password must be between 5-25 characters")) {
                                    newPasswordErrorText.setText(getResources().getString(R.string.password_length_error));
                                    newPasswordErrorText.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllErrorTexts();

                String newEmail = newEmailEditText.getText().toString();
                String confirmEmail = confirmEmailEditText.getText().toString();

                String[] args = {MainActivity.username, newEmail, confirmEmail};
                mViewModel.setArgs(args);

                mViewModel.getResult("EMAIL").observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.i(TAG, "s: " + s);

                        if (s.equals("SUCCESS")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            emptyFields();
                        }
                        else {
                            String[] errors = s.split(";");

                            for (int i = 0; i < errors.length; i++) {
                                if (errors[i].equals("All fields are required")) {
                                    Toast.makeText(getActivity(), errors[i], Toast.LENGTH_SHORT).show();
                                }
                                else if (errors[i].equals("FAILED CHANGE PROFILE: Error occurred while updating email")) {
                                    newEmailErrorText.setText(getResources().getString(R.string.email_already_taken_error));
                                    newEmailErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Emails do not match")) {
                                    confirmEmailErrorText.setText(getResources().getString(R.string.emails_not_match_error));
                                    confirmEmailErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Email is not valid")) {
                                    newEmailErrorText.setText(getResources().getString(R.string.email_not_valid_error));
                                    newEmailErrorText.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
            }
        });
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

}
