package nl.tue.besportive.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.ConfigureChallengesAdapter;
import nl.tue.besportive.data.Challenge;

public class ConfigureChallengesViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView difficultyView;
    ImageView trashButton;


    public ConfigureChallengesViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        nameView = itemView.findViewById(R.id.challenge_name);
        difficultyView = itemView.findViewById(R.id.challenge_difficulty);
        trashButton = itemView.findViewById(R.id.trash_icon_button);
    }

    public void bind(final Challenge item, final ConfigureChallengesAdapter.OnItemClickListener listener) {
        trashButton.setOnClickListener(v -> listener.onItemClick(item));
    }
}
