package nl.tue.besportive;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class JoinCreateGroupViewModel extends ViewModel {
    private final MutableLiveData<String> code;
    private final MutableLiveData<Boolean> loading;

    public JoinCreateGroupViewModel() {
        code = new MutableLiveData<>();
        loading = new MutableLiveData<>();
    }

    public MutableLiveData<String> getCode() {
        return code;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void joinGroup(Context context) {
        loading.setValue(true);
        FirebaseFunctions.getInstance().getHttpsCallable("joinGroup")
                .withTimeout(10, TimeUnit.SECONDS)
                .call(code.getValue())
                .addOnCompleteListener(task -> onGroupJoined(context, task));
    }

    private void onGroupJoined(Context context, Task<HttpsCallableResult> task) {
        if (task.isSuccessful()) {
            Navigator.navigateToFeedActivity(context, true);
        } else {
            loading.setValue(false);
            Log.e("JoinGroupModel", "Failed to join group", task.getException());
            Toast.makeText(context, "Failed to join group: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void navigateToCreateGroupActivity(Context context) {
        Navigator.navigateToCreateGroupActivity(context);
    }
}
