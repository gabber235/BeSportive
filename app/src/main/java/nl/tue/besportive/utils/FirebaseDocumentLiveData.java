package nl.tue.besportive.utils;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class FirebaseDocumentLiveData extends LiveData<DocumentSnapshot> {
    private static final String TAG = "FirebaseDocumentLiveData";

    private final String documentPath;
    private final FirebaseEventListener listener = new FirebaseEventListener();
    private ListenerRegistration listenerRegistration;
    private final Handler handler = new Handler();

    private boolean listenerRemovePending = false;
    private final Runnable removeListener = () -> {
        listenerRemovePending = false;
        listenerRegistration.remove();
        listenerRegistration = null;
    };


    public FirebaseDocumentLiveData(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    protected void onActive() {
        super.onActive();

        if (listenerRemovePending) {
            // Listener is scheduled to be removed, but the LiveData became active again before the
            // removal could happen. Cancel the pending removal as we now need the listener.
            handler.removeCallbacks(removeListener);
        } else if (listenerRegistration == null) {
            // There is no listener, so start listening.
            listenerRegistration = FirebaseFirestore.getInstance().document(documentPath).addSnapshotListener(listener);
        }

        listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        super.onInactive();

        // Remove listener after a short delay.
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }

    private class FirebaseEventListener implements com.google.firebase.firestore.EventListener<DocumentSnapshot> {
        @Override
        public void onEvent(DocumentSnapshot documentSnapshot, com.google.firebase.firestore.FirebaseFirestoreException e) {
            if (e != null) {
                Log.e(TAG, "Can't listen to document snapshot.", e);
                return;
            }
            setValue(documentSnapshot);
        }
    }


    public static String generateId() {
        return FirebaseFirestore.getInstance().collection("someCollection").document().getId();
    }
}
