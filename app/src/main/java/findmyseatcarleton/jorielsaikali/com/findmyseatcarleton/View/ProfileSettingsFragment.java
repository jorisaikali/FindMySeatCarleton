package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.ProfileSettingsViewModel;

public class ProfileSettingsFragment extends Fragment {

    private ProfileSettingsViewModel mViewModel;

    private EditText oldPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private EditText newEmailEditText, confirmEmailEditText;
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProfileSettingsViewModel.class);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                String[] args = {MainActivity.username, oldPassword, newPassword, confirmPassword};
                mViewModel.setArgs(args);

                mViewModel.getResult("PASSWORD").observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                        if (s.equals("SUCCESS")) {
                            emptyFields();
                        }
                    }
                });
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = newEmailEditText.getText().toString();
                String confirmEmail = confirmEmailEditText.getText().toString();

                String[] args = {MainActivity.username, newEmail, confirmEmail};
                mViewModel.setArgs(args);

                mViewModel.getResult("EMAIL").observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                        if (s.equals("SUCCESS")) {
                            emptyFields();
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

}
