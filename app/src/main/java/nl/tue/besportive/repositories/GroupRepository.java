package nl.tue.besportive.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.List;
import java.util.stream.Collectors;

import nl.tue.besportive.data.Group;
import nl.tue.besportive.data.Group.Member;
import nl.tue.besportive.utils.FirebaseQueryLiveData;

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
        return groupSnapshot = new FirebaseQueryLiveData(getGroupQuery());
    }

    public LiveData<Group> getLiveGroup() {
        if (group != null) {
            return group;
        }

        return group = Transformations.map(getGroupSnapshot(), input -> {
            if (input == null || input.isEmpty()) {
                return null;
            }
            List<Group> groups = input.toObjects(Group.class);

            Group group = groups.get(0);
            group.setId(input.getDocuments().get(0).getId());

            return group;
        });
    }

    public LiveData<List<Member>> getLiveMembers() {
        if (members != null) {
            return members;
        }

        return members = Transformations.map(getLiveGroup(), group -> {
            if (group == null) {
                return null;
            }
            return group.getMembers().entrySet().stream().map(entry -> {
                Member member = entry.getValue();
                member.setId(entry.getKey());
                return member;
            }).sorted().collect(Collectors.toList());
        });
    }

    public LiveData<Member> getLiveMember(String uid) {
        return Transformations.map(getLiveMembers(), members -> {
            if (members == null) {
                return null;
            }
            return members.stream().filter(member -> member.getId().equals(uid)).findFirst().orElse(null);
        });
    }

    public LiveData<Boolean> isAdministrator() {
        return Transformations.map(getLiveGroup(), group -> {
            if (group == null) {
                return false;
            }
            return group.getAdmin().equals(FirebaseAuth.getInstance().getCurrentUser().getUid());
        });
    }

    public void fetchGroupId(OnSuccessListener<String> onSuccess) {
        getGroupQuery().get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.isEmpty()) {
                onSuccess.onSuccess(null);
            } else {
                onSuccess.onSuccess(queryDocumentSnapshots.getDocuments().get(0).getId());
            }
        });
    }

    public void disbandGroup(Runnable onDisband) {
        FirebaseFunctions.getInstance().getHttpsCallable("disbandGroup").call().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onDisband.run();
            } else {
                Log.e("GroupRepository", "Failed to disband group", task.getException());
            }
        });
    }

    public void leaveGroup(Runnable onLeave) {
        FirebaseFunctions.getInstance().getHttpsCallable("leaveGroup").call().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onLeave.run();
            } else {
                Log.e("GroupRepository", "Failed to leave group", task.getException());
            }
        });
    }

    public void kickMember(String uid) {
        FirebaseFunctions.getInstance().getHttpsCallable("kickUser").call(uid).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("GroupRepository", "Kicked user " + uid);
            } else {
                Log.e("GroupRepository", "Failed to kick user " + uid, task.getException());
            }
        });
    }
}
