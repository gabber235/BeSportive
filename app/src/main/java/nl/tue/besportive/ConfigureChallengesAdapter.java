package nl.tue.besportive;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import okhttp3.Challenge;

public class ConfigureChallengesAdapter extends RecyclerView.Adapter<ConfigureChallengesViewHolder> {
    Context context;
    List<Challenges> items;
    private final OnItemClickListener listener;
    private ConfigureChallengesViewModel viewModel;

    public interface OnItemClickListener {
        void onItemClick(Challenges item);
    }

    public ConfigureChallengesAdapter(Context context, List<Challenges> items, ConfigureChallengesViewModel viewModel, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ConfigureChallengesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConfigureChallengesViewHolder(LayoutInflater.from(context).inflate(R.layout.challenges_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigureChallengesViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.difficultyView.setText(convertDifficulty(items.get(position).getDifficulty()));
        // Set an OnClickListener on the ImageView button in the ViewHolder
        holder.bind(items.get(position), listener);
        // Color
        if (items.get(position).getDifficulty() == 0) {
            holder.difficultyView.setTextColor(ContextCompat.getColor(context, R.color.colorEasy));
        } else if (items.get(position).getDifficulty() == 1) {
            holder.difficultyView.setTextColor(ContextCompat.getColor(context, R.color.colorMedium));
        } else if (items.get(position).getDifficulty() == 2) {
            holder.difficultyView.setTextColor(ContextCompat.getColor(context, R.color.colorHard));
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListenerMyChallenges {
        void onItemClickMyChallenges(Challenges challenge);
    }
    public String convertDifficulty(int difficulty) {
        switch (difficulty) {
            case 0:
                return "Easy";
            case 1:
                return "Medium";
            case 2:
                return "Hard";
            default:
                return "";
        }
    }


    public void setChallenges(List<Challenges> ChallengesList) {
        this.items = ChallengesList;
        notifyDataSetChanged();
    }
}
