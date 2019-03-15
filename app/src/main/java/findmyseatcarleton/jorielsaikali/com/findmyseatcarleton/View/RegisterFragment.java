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
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private final String TAG = "RegisterFragment";

    private RegisterViewModel mViewModel;
    private Button registerButton;
    private EditText usernameEditText, passwordEditText, confirmPasswordEditText, emailEditText, confirmEmailEditText;
    private TextView usernameErrorText, passwordErrorText, confirmPasswordErrorText, emailErrorText, confirmEmailErrorText;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        // --------------- Finding all components --------------- //
        registerButton = view.findViewById(R.id.registerButton);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        confirmEmailEditText = view.findViewById(R.id.confirmEmailEditText);

        usernameErrorText = view.findViewById(R.id.usernameErrorText);
        passwordErrorText = view.findViewById(R.id.passwordErrorText);
        confirmPasswordErrorText = view.findViewById(R.id.confirmPasswordErrorText);
        emailErrorText = view.findViewById(R.id.emailErrorText);
        confirmEmailErrorText = view.findViewById(R.id.confirmEmailErrorText);
        // ----------------------------------------------------- //

        //-------------- REGISTER BUTTON LISTENER -------------- //
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Clear all error messages from previous register attempt (even if non-existent)
                clearAllErrorTexts();

                // 2. Get all user given data from input fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String confirmEmail = confirmEmailEditText.getText().toString();

                // 3. Set args for RegisterViewModel to use
                String[] args = {username, password, confirmPassword, email, confirmEmail};
                mViewModel.setArgs(args);

                // 4. Observe when the RegisterViewModel result LiveData changes
                mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        // ======= Change happened in RegisterViewModel ======= //

                        // 4.1) if the result was SUCCESS
                        if (s.equals("SUCCESS")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show(); // display toast to user with success

                            // Change fragment back to LoginFragment
                            LoginFragment loginFragment = new LoginFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.loginLayout, loginFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        // 4.2) if the result was not successful
                        else {
                            // split the error string but ; and display
                            String[] errors = s.split(";");
                            displayErrors(errors);
                        }
                    }
                });
            }
        });
        // ----------------------------------------------------- //

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    private void clearAllErrorTexts() {
        usernameErrorText.setVisibility(View.GONE);
        passwordErrorText.setVisibility(View.GONE);
        confirmPasswordErrorText.setVisibility(View.GONE);
        emailErrorText.setVisibility(View.GONE);
        confirmEmailErrorText.setVisibility(View.GONE);
    }

    private void displayErrors(String[] errors) {
        // Iterate through each error in errors and set and display corresponding field
        for (String error: errors) {
            switch (error) {
                case "Username already taken":
                    usernameErrorText.setText(getResources().getString(R.string.username_already_taken_error));
                    usernameErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Username must be between 5-25 characters":
                    usernameErrorText.setText(getResources().getString(R.string.username_length_error));
                    usernameErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Passwords do not match":
                    confirmPasswordErrorText.setText(getResources().getString(R.string.password_not_match_error));
                    confirmPasswordErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Password must be between 5-25 characters":
                    passwordErrorText.setText(getResources().getString(R.string.password_length_error));
                    passwordErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Email already taken":
                    emailErrorText.setText(getResources().getString(R.string.email_already_taken_error));
                    emailErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Emails do not match":
                    confirmEmailErrorText.setText(getResources().getString(R.string.emails_not_match_error));
                    confirmEmailErrorText.setVisibility(View.VISIBLE);
                    break;
                case "Email is not valid":
                    emailErrorText.setText(getResources().getString(R.string.email_not_valid_error));
                    emailErrorText.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

}
