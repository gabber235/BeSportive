package nl.tue.besportive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.tue.besportive.R;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.data.Difficulty;
import nl.tue.besportive.holders.DefaultChallengesViewHolder;
import nl.tue.besportive.models.ConfigureChallengesViewModel;

public class DefaultChallengesAdapter extends RecyclerView.Adapter<DefaultChallengesViewHolder> {
    Context context;
    List<Challenge> items;
    private final OnItemClickListener listener;
    private ConfigureChallengesViewModel viewModel;

    public interface OnItemClickListener {
        void onItemClick(Challenge item);
    }

    public DefaultChallengesAdapter(Context context, List<Challenge> items, ConfigureChallengesViewModel viewModel, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.viewModel = viewModel;
    }


    @NonNull
    @Override
    public DefaultChallengesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefaultChallengesViewHolder(LayoutInflater.from(context).inflate(R.layout.default_challenges_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultChallengesViewHolder holder, int position) {
        Challenge challenge = items.get(position);
        Difficulty difficulty = challenge.getSmartDifficulty();

        holder.nameView.setText(challenge.getName());
        holder.difficultyView.setText(difficulty.getName());
        holder.difficultyView.setTextColor(difficulty.getColor());
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setChallenges(List<Challenge> ChallengesList) {
        this.items = ChallengesList;
        notifyDataSetChanged();
    }
}
