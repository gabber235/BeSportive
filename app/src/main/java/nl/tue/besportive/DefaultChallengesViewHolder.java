package nl.tue.besportive;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import okhttp3.Challenge;

public class DefaultChallengesViewHolder extends RecyclerView.ViewHolder {
    TextView nameView,difficultyView;
    ImageView trashButton;

    public DefaultChallengesViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        nameView = itemView.findViewById(R.id.challenge_name);
        difficultyView = itemView.findViewById(R.id.challenge_difficulty);
        trashButton = itemView.findViewById(R.id.add_button);
    }
    public void bind(final Challenges item, final DefaultChallengesAdapter.OnItemClickListener listener) {
        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}

