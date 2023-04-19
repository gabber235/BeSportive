package nl.tue.besportive.adapters;

import android.content.Context;
import java.util.ArrayList;

import nl.tue.besportive.R;
import nl.tue.besportive.data.Group;
import nl.tue.besportive.databinding.LeaderboardCardBinding;

public class LeaderboardAdapter extends BaseAdapter<LeaderboardCardBinding, Group.Member> {

    private final LeaderboardAdapterListener listener;

    public LeaderboardAdapter(LeaderboardAdapterListener listener) {
        super(new ArrayList<>(), R.layout.leaderboard_card);
        this.listener = listener;
    }

    @Override
    protected void bind(LeaderboardCardBinding binding, int position, Group.Member item) {
        binding.setPosition(position);
        binding.setMember(item);
        binding.setListener(listener);
    }

    public interface LeaderboardAdapterListener {
        void onMemberClicked(Context context, Group.Member member);
    }
}
