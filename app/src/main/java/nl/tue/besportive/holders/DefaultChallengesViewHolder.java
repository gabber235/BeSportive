package nl.tue.besportive.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import nl.tue.besportive.R;
import nl.tue.besportive.adapters.DefaultChallengesAdapter;
import nl.tue.besportive.data.Challenge;

public class DefaultChallengesViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView difficultyView;
    ImageView trashButton;

    public DefaultChallengesViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        nameView = itemView.findViewById(R.id.challenge_name);
        difficultyView = itemView.findViewById(R.id.challenge_difficulty);
        trashButton = itemView.findViewById(R.id.add_button);
    }

    public void bind(final Challenge item, final DefaultChallengesAdapter.OnItemClickListener listener) {
        trashButton.setOnClickListener(v -> listener.onItemClick(item));
    }
}

