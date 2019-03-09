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

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder> {
    private List<String> options = new ArrayList<>();
    private OnItemClickListener listener;

    public SettingsAdapter(List<String> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public SettingsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.settings_item, viewGroup, false);

        return new SettingsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsHolder settingsHolder, int i) {
        String currentOption = options.get(i);
        settingsHolder.optionTextView.setText(currentOption);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class SettingsHolder extends RecyclerView.ViewHolder {
        private TextView optionTextView;

        public SettingsHolder(@NonNull View itemView) {
            super(itemView);
            optionTextView = itemView.findViewById(R.id.optionTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(options.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String option);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
