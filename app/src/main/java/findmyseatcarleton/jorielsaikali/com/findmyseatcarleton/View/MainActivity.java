package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class MainActivity extends AppCompatActivity {

    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            username = intent.getStringExtra("username");
        }
        else {
            username = "";
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.homeLayout, new HomeFragment()).commit();
    }
}
