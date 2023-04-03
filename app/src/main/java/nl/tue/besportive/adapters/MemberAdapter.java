package nl.tue.besportive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.tue.besportive.R;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.holders.MyViewHolder;


public class MemberAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Member> items;

    public MemberAdapter(Context context, List<Member> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.member_view, parent, false));
    }

    public Member getMember(int position) {
        return items.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Member member = getMember(position);
        holder.nameView.setText(member.getName());
//        holder.emailView.setText(items.get(position).getEmail());
//        holder.imageView.setImageResource(items.get(position).getImage());
        holder.pointsView.setText(String.valueOf(member.getPoints()));
    }

    public void setMembers(List<Member> members) {
        this.items = members;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
