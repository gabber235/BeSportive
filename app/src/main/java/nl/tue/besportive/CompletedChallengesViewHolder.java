package nl.tue.besportive;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedChallengesViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView,descriptionView, pointsView, difficultyView, timeOfCompletionView;

    public CompletedChallengesViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.profile_picture);
        nameView = itemView.findViewById(R.id.challenge_name);
        descriptionView = itemView.findViewById(R.id.challenge_description);
        difficultyView = itemView.findViewById(R.id.challenge_difficulty);
        timeOfCompletionView = itemView.findViewById(R.id.challenge_time);
    }
}
