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

public class FloorListAdapter extends RecyclerView.Adapter<FloorListAdapter.FloorHolder> {

    private List<String> floors = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FloorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.floor_item, viewGroup, false);
        return new FloorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorHolder floorHolder, int i) {
        String currentFloor = floors.get(i);
        floorHolder.floorNameTextView.setText(currentFloor);
    }

    @Override
    public int getItemCount() {
        return floors.size();
    }

    public void setFloors(List<String> floors) {
        this.floors = floors;
        notifyDataSetChanged();
    }

    class FloorHolder extends RecyclerView.ViewHolder {
        private TextView floorNameTextView;

        public FloorHolder(@NonNull View itemView) {
            super(itemView);
            floorNameTextView = itemView.findViewById(R.id.floorNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(floors.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String building);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
