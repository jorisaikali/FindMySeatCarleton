package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class BuildingListAdapter extends RecyclerView.Adapter<BuildingListAdapter.BuildingHolder> {

    @NonNull
    @Override
    public BuildingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingHolder buildingHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BuildingHolder extends RecyclerView.ViewHolder {
        private TextView buildingNameTextView;

        public BuildingHolder(@NonNull View itemView) {
            super(itemView);
            buildingNameTextView = itemView.findViewById(R.id.buildingNameTextView);
        }
    }
}
