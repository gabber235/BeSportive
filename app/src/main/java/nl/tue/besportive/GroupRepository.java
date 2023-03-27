package nl.tue.besportive;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupRepository {
    private final MutableLiveData<Group> group = new MutableLiveData<>();
    private final MutableLiveData<List<Member>> membersLiveData = new MutableLiveData<>();
    private final FirebaseFirestore firestore;

    public GroupRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<Group> getGroup() {
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
                }
            }
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
