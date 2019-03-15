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
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private Button loginButton, registerButton;
    private EditText usernameEditText, passwordEditText;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);


        // ------------- Finding all components ------------ //
        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.registerButton);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        // ------------------------------------------------- //

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        // ---------------------- LOGIN BUTTON LISTENER ----------------------- //
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ---------- Getting user given data from input fields ---------- //
                final String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // --------------------------------------------------------------- //

                String[] args = {username, password}; // setting arguments for LoginViewModel to use
                mViewModel.setArgs(args);

                // ------------------- Observe result LiveData from LoginViewModel ------------------ //
                mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        // If the result was a success
                        if (s.equals("SUCCESSFUL LOGIN")) {
                            // Start the MainActivity and pass username
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                        else if (s.equals("All fields required")) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // ---------------------------------------------------------------------------------- //
            }
        });
        // -------------------------------------------------------------------- //

        // ---------------------- REGISTER BUTTON LISTENER ----------------------- //
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.loginLayout, registerFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        // ---------------------------------------------------------------------- //
    }

}
