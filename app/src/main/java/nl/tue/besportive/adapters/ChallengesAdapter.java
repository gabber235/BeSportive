package nl.tue.besportive.adapters;

import android.content.Context;

import java.util.ArrayList;

import nl.tue.besportive.R;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.databinding.ChallengeCardBinding;

public class ChallengesAdapter extends BaseAdapter<ChallengeCardBinding, Challenge> {
    private final ChallengesAdapterListener listener;

    public ChallengesAdapter(ChallengesAdapterListener listener) {
        super(new ArrayList<>(), R.layout.challenge_card);
        this.listener = listener;
    }

    @Override
    protected void bind(ChallengeCardBinding binding, Challenge item) {
        binding.setChallenge(item);
        binding.setListener(listener);
    }

    public static interface ChallengesAdapterListener {
        void onStart(Context context, Challenge challenge);
    }
}
