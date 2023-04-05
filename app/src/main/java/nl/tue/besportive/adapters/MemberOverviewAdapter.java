package nl.tue.besportive.adapters;

import java.util.ArrayList;

import nl.tue.besportive.R;
import nl.tue.besportive.data.FeedItem;
import nl.tue.besportive.databinding.FeedCardBinding;

public class MemberOverviewAdapter extends BaseAdapter<FeedCardBinding, FeedItem> {
    private final FeedAdapter.FeedAdapterListener listener;

    public MemberOverviewAdapter(FeedAdapter.FeedAdapterListener listener) {
        super(new ArrayList<>(), R.layout.feed_card);
        this.listener = listener;
    }

    @Override
    protected void bind(FeedCardBinding binding, int position, FeedItem item) {
        binding.setMember(item.getMember());
        binding.setCompletedChallenge(item.getChallenge());
        binding.setListener(listener);
    }
}
