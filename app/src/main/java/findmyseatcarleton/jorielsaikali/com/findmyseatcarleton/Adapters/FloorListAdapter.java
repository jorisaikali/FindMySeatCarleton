package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.R;

public class FloorListAdapter extends RecyclerView.Adapter<FloorListAdapter.FloorHolder> {

    @NonNull
    @Override
    public FloorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FloorHolder floorHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FloorHolder extends RecyclerView.ViewHolder {
        private TextView floorNameTextView;

        public FloorHolder(@NonNull View itemView) {
            super(itemView);
            floorNameTextView = itemView.findViewById(R.id.floorNameTextView);
        }
    }
}
