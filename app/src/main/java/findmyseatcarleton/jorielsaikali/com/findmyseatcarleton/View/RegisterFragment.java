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
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private Button registerButton;
    private EditText usernameEditText, passwordEditText, emailEditText;

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
        emailEditText = view.findViewById(R.id.emailEditText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                String[] args = {"REGISTER", username, email, password};
                mViewModel.setArgs(args);

                mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                        if (s.equals("SUCCESS")) {
                            LoginFragment loginFragment = new LoginFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.loginLayout, loginFragment)
                                    .addToBackStack(null)
                                    .commit();
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

}
