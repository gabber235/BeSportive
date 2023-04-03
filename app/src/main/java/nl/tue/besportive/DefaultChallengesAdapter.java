package nl.tue.besportive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DefaultChallengesAdapter extends RecyclerView.Adapter<DefaultChallengesViewHolder> {
    Context context;
    List<Challenges> items;
    private final OnItemClickListener listener;
    private ConfigureChallengesViewModel viewModel;

    public interface OnItemClickListener {
        void onItemClick(Challenges item);
    }

    public DefaultChallengesAdapter(Context context, List<Challenges> items, ConfigureChallengesViewModel viewModel, OnItemClickListener listener) {
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
        holder.nameView.setText(items.get(position).getName());
        holder.difficultyView.setText(convertDifficulty(items.get(position).getDifficulty()));
        holder.bind(items.get(position), listener);
        // Color
        if (items.get(position).getDifficulty() == 0) {
            holder.difficultyView.setTextColor(ContextCompat.getColor(context, R.color.easy_green));
        } else if (items.get(position).getDifficulty() == 1) {
            holder.difficultyView.setTextColor(ContextCompat.getColor(context, R.color.medium_orange));
        } else if (items.get(position).getDifficulty() == 2) {
            holder.difficultyView.setTextColor(ContextCompat.getColor(context, R.color.hard_red));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setChallenges(List<Challenges> ChallengesList) {
        this.items = ChallengesList;
        notifyDataSetChanged();
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
}
