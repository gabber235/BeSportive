package nl.tue.besportive.adapters;

import java.util.ArrayList;

import nl.tue.besportive.R;
import nl.tue.besportive.data.Challenge;
import nl.tue.besportive.databinding.DefaultChallengesCardBinding;
import nl.tue.besportive.databinding.GroupChallengesCardBinding;

public class ConfigureChallengesAdapter {
    public static class GroupChallengesAdapter extends BaseAdapter<GroupChallengesCardBinding, Challenge> {
        private final GroupChallengesAdapterListener listener;

        public GroupChallengesAdapter(GroupChallengesAdapterListener listener) {
            super(new ArrayList<>(), R.layout.group_challenges_card);
            this.listener = listener;

        }

        @Override
        protected void bind(GroupChallengesCardBinding binding, int position, Challenge item) {
            binding.setChallenge(item);
            binding.setListener(listener);
        }

        public interface GroupChallengesAdapterListener {
            void onRemove(Challenge challenge);
        }
    }

    public static class DefaultChallengesAdapter extends BaseAdapter<DefaultChallengesCardBinding, Challenge> {
        private final DefaultChallengesAdapterListener listener;

        public DefaultChallengesAdapter(DefaultChallengesAdapterListener listener) {
            super(new ArrayList<>(), R.layout.default_challenges_card);
            this.listener = listener;

        }

        @Override
        protected void bind(DefaultChallengesCardBinding binding, int position, Challenge item) {
            binding.setChallenge(item);
            binding.setListener(listener);
        }

        public interface DefaultChallengesAdapterListener {
            void onAdd(Challenge challenge);
        }
    }
}
