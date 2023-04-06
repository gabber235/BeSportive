package nl.tue.besportive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import nl.tue.besportive.R;
import nl.tue.besportive.data.Difficulty;
import nl.tue.besportive.databinding.AddChallengeDifficultyItemBinding;

public class AddChallengeDifficultyAdapter extends ArrayAdapter<Difficulty> {
    public AddChallengeDifficultyAdapter(@NonNull Context context) {
        super(context, R.layout.add_challenge_difficulty_item, Difficulty.values());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        BaseViewHolder<AddChallengeDifficultyItemBinding> holder;
        if (row == null) {
            holder = new BaseViewHolder<>(AddChallengeDifficultyItemBinding.inflate(LayoutInflater.from(getContext()), parent, false));
            row = holder.getBinding().getRoot();
            row.setTag(holder);
        } else {
            holder = (BaseViewHolder<AddChallengeDifficultyItemBinding>) row.getTag();
        }

        holder.getBinding().setDifficulty(getItem(position));
        
        return row;
    }
}
