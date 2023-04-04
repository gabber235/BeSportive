package nl.tue.besportive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.tue.besportive.R;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.holders.CompletedChallengesViewHolder;

public class CompletedChallengesAdapter extends RecyclerView.Adapter<CompletedChallengesViewHolder> {
    Context context;
    List<CompletedChallenge> items;

    public CompletedChallengesAdapter(Context context, List<CompletedChallenge> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CompletedChallengesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompletedChallengesViewHolder(LayoutInflater.from(context).inflate(R.layout.completed_challenges_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedChallengesViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.difficultyView.setText(String.valueOf(items.get(position).getSmartDifficulty().getName()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setCompletedChallenges(List<CompletedChallenge> completedChallenges) {
        this.items = completedChallenges;
        notifyDataSetChanged();
    }
}
