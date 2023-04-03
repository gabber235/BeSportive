package nl.tue.besportive.utils;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseQueryLiveData extends LiveData<QuerySnapshot> {
    public static final String TAG = "FirebaseQueryLiveData";

    private Query query;
    private final FirebaseEventListener listener = new FirebaseEventListener();
    private ListenerRegistration listenerRegistration;
    private Handler handler = new Handler();

    private boolean listenerRemovePending = false;
    private final Runnable removeListener = () -> {
        listenerRemovePending = false;
        listenerRegistration.remove();
        listenerRegistration = null;
    };


    public FirebaseQueryLiveData(Query query) {
        this.query = query;
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
            listenerRegistration = query.addSnapshotListener(listener);
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

    private class FirebaseEventListener implements com.google.firebase.firestore.EventListener<QuerySnapshot> {
        @Override
        public void onEvent(QuerySnapshot queryDocumentSnapshots, com.google.firebase.firestore.FirebaseFirestoreException e) {
            if (e != null) {
                Log.e(TAG, "Can't listen to query snapshots.", e);
                return;
            }
            setValue(queryDocumentSnapshots);
        }
    }
}
