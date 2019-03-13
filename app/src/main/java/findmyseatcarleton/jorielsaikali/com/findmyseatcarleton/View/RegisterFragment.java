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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllErrorTexts();

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String confirmEmail = confirmEmailEditText.getText().toString();

                String[] args = {username, password, confirmPassword, email, confirmEmail};
                mViewModel.setArgs(args);

                mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Log.i(TAG, s);

                        if (s.equals("SUCCESS")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                            LoginFragment loginFragment = new LoginFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.loginLayout, loginFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        else {
                            String[] errors = s.split(";");

                            for (int i = 0; i < errors.length; i++) {
                                if (errors[i].equals("Username already taken")) {
                                    usernameErrorText.setText(getResources().getString(R.string.username_already_taken_error));
                                    usernameErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Username must be between 5-25 characters")) {
                                    usernameErrorText.setText(getResources().getString(R.string.username_length_error));
                                    usernameErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Passwords do not match")) {
                                    confirmPasswordErrorText.setText(getResources().getString(R.string.password_not_match_error));
                                    confirmPasswordErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Password must be between 5-25 characters")) {
                                    passwordErrorText.setText(getResources().getString(R.string.password_length_error));
                                    passwordErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Email already taken")) {
                                    emailErrorText.setText(getResources().getString(R.string.email_already_taken_error));
                                    emailErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Emails do not match")) {
                                    confirmEmailErrorText.setText(getResources().getString(R.string.emails_not_match_error));
                                    confirmEmailErrorText.setVisibility(View.VISIBLE);
                                }
                                else if (errors[i].equals("Email is not valid")) {
                                    emailErrorText.setText(getResources().getString(R.string.email_not_valid_error));
                                    emailErrorText.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
            }
        });

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

}
