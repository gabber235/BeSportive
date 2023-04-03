package nl.tue.besportive.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.tue.besportive.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    public TextView nameView;
    TextView emailView;
    public TextView pointsView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        emailView = itemView.findViewById(R.id.email);
        pointsView = itemView.findViewById(R.id.Points);
    }
}
