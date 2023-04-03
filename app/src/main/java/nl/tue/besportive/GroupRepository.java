package nl.tue.besportive;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.Query;
import javax.swing.GroupLayout.Group;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class GroupRepository {
    private LiveData<Group> group;
    private LiveData<QuerySnapshot> groupSnapshot;
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
    public MutableLiveData<List<Member>> getMemberItems () {
        Log.i("GroupRepository", "Getting group");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        firestore.collection("groups").whereNotEqualTo("members." + uid, null).limit(1).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("GroupRepository", "Failed to get group", error);
                return;
            }
            if (value != null) {
                Log.i("GroupRepository", "Got group: " + value);
                DocumentSnapshot document = value.getDocuments().get(0);
                String groupId = document.getId(); // get the ID of the group
                List<Group> groups = value.toObjects(Group.class);
                if (groups.size() > 0) {
                    // add the id of the group to the group object
                    Group groupObject = groups.get(0);
                    groupObject.setGroupId(groupId);
                    group.setValue(groupObject);
                    List<Member> items = new ArrayList<Member>();
                    Map<String, Group.Member> membersList = group.getValue().getMembers();
                    for (Map.Entry<String, Group.Member> entry : membersList.entrySet()) {
                        //System.out.println(entry);
                        String name = (String) entry.getValue().getName();
                        String photoUrl = (String) entry.getValue().getPhotoUrl();
                        items.add(new Member(name, "test_email", R.drawable.a, 10, entry.getKey()));
                    }
                    membersLiveData.setValue(items);
                }
            }
        });
        return membersLiveData;
    }
}
