package nl.tue.besportive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.tue.besportive.R;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.holders.ConfigureChallengesViewHolder;
import nl.tue.besportive.models.ConfigureChallengesViewModel;

public class ConfigureChallengesAdapter extends RecyclerView.Adapter<ConfigureChallengesViewHolder> {
    Context context;
    List<Challenge> items;
    private final OnItemClickListener listener;
    private ConfigureChallengesViewModel viewModel;

    public interface OnItemClickListener {
        void onItemClick(Challenge item);
    }

    public ConfigureChallengesAdapter(Context context, List<Challenge> items, ConfigureChallengesViewModel viewModel, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ConfigureChallengesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConfigureChallengesViewHolder(LayoutInflater.from(context).inflate(R.layout.challenges_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigureChallengesViewHolder holder, int position) {
        Challenge challenge = items.get(position);
        holder.nameView.setText(challenge.getName());
        holder.difficultyView.setText(challenge.getSmartDifficulty().getName());
        holder.difficultyView.setTextColor(challenge.getSmartDifficulty().getColor());
        // Set an OnClickListener on the ImageView button in the ViewHolder
        holder.bind(challenge, listener);
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
