package nl.tue.besportive.adapters;

import android.content.Context;

import java.util.ArrayList;

import nl.tue.besportive.R;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.data.FeedItem;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.databinding.FeedCardBinding;

public class FeedAdapter extends BaseAdapter<FeedCardBinding, FeedItem> {
    private final FeedAdapterListener listener;

    public FeedAdapter(FeedAdapterListener listener) {
        super(new ArrayList<>(), R.layout.feed_card);
        this.listener = listener;
    }

    @Override
    protected void bind(FeedCardBinding binding, int position, FeedItem item) {
        binding.setCompletedChallenge(item.getChallenge());
        binding.setMember(item.getMember());
        binding.setListener(listener);
    }

    public static interface FeedAdapterListener {
        void onLove(CompletedChallenge completedChallenge);

        void openMember(Context context, Member member);
    }
}
