package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;
import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.ViewModel.FindSeatViewModel;

public class SeatAmountFragment extends Fragment {

    private FindSeatViewModel mViewModel;
    private ImageButton oneSeatButton, twoSeatButton, threeSeatButton, fourSeatButton;
    private String selectedAmount;

    public static SeatAmountFragment newInstance() {
        return new SeatAmountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seat_amount_fragment, container, false);

        oneSeatButton = view.findViewById(R.id.oneSeatButton);
        twoSeatButton = view.findViewById(R.id.twoSeatButton);
        threeSeatButton = view.findViewById(R.id.threeSeatButton);
        fourSeatButton = view.findViewById(R.id.fourSeatButton);

        oneSeatButton.setOnClickListener(new ButtonListener());
        twoSeatButton.setOnClickListener(new ButtonListener());
        threeSeatButton.setOnClickListener(new ButtonListener());
        fourSeatButton.setOnClickListener(new ButtonListener());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(FindSeatViewModel.class);
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mViewModel.setSeatAmount(v.getTag().toString());

            BuildingListFragment buildingListFragment = new BuildingListFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeLayout, buildingListFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
