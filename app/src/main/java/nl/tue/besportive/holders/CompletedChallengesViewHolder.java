package nl.tue.besportive.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.tue.besportive.R;

public class CompletedChallengesViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    public TextView nameView;
    TextView descriptionView;
    TextView pointsView;
    public TextView difficultyView;
    TextView timeOfCompletionView;

    public CompletedChallengesViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.profile_picture);
        nameView = itemView.findViewById(R.id.challenge_name);
        difficultyView = itemView.findViewById(R.id.challenge_difficulty);
        timeOfCompletionView = itemView.findViewById(R.id.challenge_time);
    }
}
