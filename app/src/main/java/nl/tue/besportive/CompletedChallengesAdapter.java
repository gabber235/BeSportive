package nl.tue.besportive;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedChallengesAdapter extends RecyclerView.Adapter<CompletedChallengesViewHolder>{
    Context context;
    List<CompletedChallenges> items;

    public CompletedChallengesAdapter(Context context, List<CompletedChallenges> items) {
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
        holder.difficultyView.setText(String.valueOf(items.get(position).getStatus()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setCompletedChallenges(List<CompletedChallenges> completedChallengesList) {
        this.items = completedChallengesList;
        notifyDataSetChanged();
    }
}
