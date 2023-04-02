package nl.tue.besportive;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import okhttp3.Challenge;

public class ConfigureChallengesViewHolder extends RecyclerView.ViewHolder {
    TextView nameView,difficultyView;
    ImageView trashButton;


    public ConfigureChallengesViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        nameView = itemView.findViewById(R.id.challenge_name);
        difficultyView = itemView.findViewById(R.id.challenge_difficulty);
        trashButton = itemView.findViewById(R.id.trash_icon_button);
    }
    public void bind(final Challenges item, final ConfigureChallengesAdapter.OnItemClickListener listener) {
        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}
