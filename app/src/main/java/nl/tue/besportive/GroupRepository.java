package nl.tue.besportive;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.stream.Collectors;

import nl.tue.besportive.Group.Member;

public class GroupRepository {
    private LiveData<QuerySnapshot> groupSnapshot;
    private LiveData<Group> group;
    private LiveData<List<Member>> members;
    private final FirebaseFirestore firestore;

    public GroupRepository() {
        firestore = FirebaseFirestore.getInstance();
    }


    private Query getGroupQuery() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        return firestore.collection("groups").whereNotEqualTo("members." + uid, null).limit(1);
    }

    public LiveData<QuerySnapshot> getGroupSnapshot() {
        if (groupSnapshot != null) {
            return groupSnapshot;
        }
        groupSnapshot = new FirebaseQueryLiveData(getGroupQuery());
        return groupSnapshot;
    }

    public LiveData<Group> getLiveGroup() {
        if (group != null) {
            return group;
        }

        group = Transformations.map(getGroupSnapshot(), input -> {
            if (input == null || input.isEmpty()) {
                return null;
            }
            List<Group> groups = input.toObjects(Group.class);
            Group group = groups.get(0);
            group.setId(input.getDocuments().get(0).getId());
            return group;
        });

        return group;
    }

    public LiveData<List<Member>> getLiveMembers() {
        if (members != null) {
            return members;
        }

        members = Transformations.map(getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return group.getMembers().entrySet().stream().map(entry -> {
                Member member = entry.getValue();
                member.setId(entry.getKey());
                return member;
            }).collect(Collectors.toList());
        });

        return members;
    }
}
