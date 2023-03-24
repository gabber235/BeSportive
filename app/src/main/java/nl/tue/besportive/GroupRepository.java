package nl.tue.besportive;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GroupRepository {
    private final MutableLiveData<Group> group = new MutableLiveData<>();
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
                List<Group> groups = value.toObjects(Group.class);
                if (groups.size() > 0) {
                    group.setValue(groups.get(0));
                }
            }
        });
        return group;
    }
}
