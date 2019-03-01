package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.loginLayout, new LoginFragment()).commit();
    }
}
