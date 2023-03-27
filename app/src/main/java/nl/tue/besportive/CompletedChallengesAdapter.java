package nl.tue.besportive;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedChallengesAdapter extends RecyclerView.Adapter<CompletedChallengesViewHolder>{
    Context context;
    List<CompletedChallengesClass> items;

    public CompletedChallengesAdapter(Context context, List<CompletedChallengesClass> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CompletedChallengesViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new CompletedChallengesViewHolder(LayoutInflater.from(context).inflate(R.layout.completed_challenges_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  CompletedChallengesViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.descriptionView.setText(items.get(position).getDescription());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.timeOfCompletionView.setText(items.get(position).getTimeOfCompletion());
        holder.difficultyView.setText(items.get(position).getDifficulty());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
