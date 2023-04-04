package nl.tue.besportive.adapters;

import java.util.ArrayList;

import nl.tue.besportive.R;
import nl.tue.besportive.data.CompletedChallenge;
import nl.tue.besportive.databinding.FeedCardBinding;

public class FeedAdapter extends BaseAdapter<FeedCardBinding, CompletedChallenge> {
    private final FeedAdapterListener listener;

    public FeedAdapter(FeedAdapterListener listener) {
        super(new ArrayList<>(), R.layout.feed_card);
        this.listener = listener;
    }

    @Override
    protected void bind(FeedCardBinding binding, CompletedChallenge item) {
        binding.setCompletedChallenge(item);
        binding.setListener(listener);
    }

    public static interface FeedAdapterListener {
        void onLike(CompletedChallenge completedChallenge);
    }
}
