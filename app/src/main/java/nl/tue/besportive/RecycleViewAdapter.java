package nl.tue.besportive;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<Challenge> challengesList;
    Context context;
    private RecycleViewClickListener listener;


    public RecycleViewAdapter(List<Challenge> challengesList, RecycleViewClickListener listener) {
        this.challengesList = challengesList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_challenge, parent, false);
        MyViewHolder holder = new MyViewHolder((view));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_ChallengeName.setText(challengesList.get(position).getName());
        holder.tv_ChDifficulty.setText(String.valueOf(challengesList.get(position).getDifficulty()));
        holder.ib_gotoactivechallenges.setTag(String.valueOf(challengesList.get(position).getClass()));
        holder.tv_ChDifficulty.setText(challengesList.get(position).getName());
        int difficulty = challengesList.get(position).getDifficulty();
        String difficultyText = "";
        int difficultyColor = 0;
        switch (difficulty) {
            case 0:
                difficultyText = "Easy";
                difficultyColor = Color.GREEN;
                break;
            case 1:
                difficultyText = "Medium";
                difficultyColor = Color.parseColor("#FFA500"); // Orange color
                break;
            case 2:
                difficultyText = "Hard";
                difficultyColor = Color.RED;
                break;
        }
        holder.tv_ChDifficulty.setText(difficultyText);
        holder.tv_ChDifficulty.setTextColor(difficultyColor);
    }

    @Override
    public int getItemCount() {
        return challengesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton ib_gotoactivechallenges;
        TextView tv_ChallengeName;
        TextView tv_ChDifficulty;


        public MyViewHolder(@NonNull View view) {
            super(view);
            ib_gotoactivechallenges = view.findViewById(R.id.ib_gotoactivechallenges);
            tv_ChallengeName = view.findViewById(R.id.tv_challengeName);
            tv_ChDifficulty = view.findViewById(R.id.tv_challengeDifficulty);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecycleViewClickListener {
        void onClick(View v, int position);

    }
}
