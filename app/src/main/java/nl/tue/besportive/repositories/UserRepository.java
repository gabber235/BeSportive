package nl.tue.besportive.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import nl.tue.besportive.data.SportiveUser;
import nl.tue.besportive.utils.FirebaseDocumentLiveData;

public class UserRepository {
    private LiveData<DocumentSnapshot> userSnapshot;
    private LiveData<SportiveUser> user;
    public UserRepository() {
    }

    public LiveData<DocumentSnapshot> getUserSnapshot() {
        if (userSnapshot != null) {
            return userSnapshot;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uid = user.getUid();
        return userSnapshot = new FirebaseDocumentLiveData("users/"+ uid);
    }

    public LiveData<SportiveUser> getLiveUser() {
        if (user != null) {
            return user;
        }

        return user = Transformations.map(getUserSnapshot(), input -> {
            if (input == null) {
                return null;
            }
            SportiveUser user= input.toObject(SportiveUser.class);
            Log.d("UserRepository", String.valueOf(user));
            return user;
        });
    }
}
