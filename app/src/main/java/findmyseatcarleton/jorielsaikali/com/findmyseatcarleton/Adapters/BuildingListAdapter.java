package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class BuildingListAdapter extends RecyclerView.Adapter<BuildingListAdapter.BuildingHolder> {
    private List<String> buildings = new ArrayList<>();

    @NonNull
    @Override
    public BuildingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.building_item, viewGroup, false);
        return new BuildingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingHolder buildingHolder, int i) {
        String currentBuilding = buildings.get(i);
        buildingHolder.buildingNameTextView.setText(currentBuilding);
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public void setBuildings(List<String> buildings) {
        this.buildings = buildings;
        notifyDataSetChanged();
    }

    class BuildingHolder extends RecyclerView.ViewHolder {
        private TextView buildingNameTextView;

        public BuildingHolder(@NonNull View itemView) {
            super(itemView);
            buildingNameTextView = itemView.findViewById(R.id.buildingNameTextView);
        }
    }
}
