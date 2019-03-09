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
import android.widget.TextView;
import android.widget.Toast;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private TextView usernameTextView, emailTextView, currentEntriesTextView, totalEntriesTextView;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        currentEntriesTextView = view.findViewById(R.id.currentEntriesTextView);
        totalEntriesTextView = view.findViewById(R.id.totalEntriesTextView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);

        mViewModel.setUsername(MainActivity.username);

        mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

                if (s.equals("SUCCESS")) {
                    usernameTextView.setText(MainActivity.username);
                    emailTextView.setText(mViewModel.getEmail());

                    String tempCurrentEntries = currentEntriesTextView.getText().toString();
                    int indexOfColon = tempCurrentEntries.indexOf(":");
                    StringBuilder sb = new StringBuilder();
                    sb.append(tempCurrentEntries.substring(0, indexOfColon+1));
                    sb.append(" ");
                    sb.append(mViewModel.getCurrentEntries());
                    currentEntriesTextView.setText(sb.toString());

                    String tempTotalEntries = totalEntriesTextView.getText().toString();
                    indexOfColon = tempTotalEntries.indexOf(":");
                    sb = new StringBuilder();
                    sb.append(tempTotalEntries.substring(0, indexOfColon+1));
                    sb.append(" ");
                    sb.append(mViewModel.getTotalEntries());
                    totalEntriesTextView.setText(sb.toString());
                }

            }
        });

    }

}
