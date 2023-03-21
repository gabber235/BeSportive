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

public class CreateGroupViewModel extends ViewModel {
    private final MutableLiveData<String> name;
    private final MutableLiveData<Boolean> loading;

    public CreateGroupViewModel() {
        name = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        loading.setValue(false);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void createGroup(Context context) {
        loading.setValue(true);
        FirebaseFunctions.getInstance().getHttpsCallable("createGroup")
                .withTimeout(10, TimeUnit.SECONDS)
                .call(name.getValue())
                .addOnCompleteListener(task -> onGroupCreated(context, task));
    }

    private void onGroupCreated(Context context, Task<HttpsCallableResult> task) {
        if (task.isSuccessful()) {
            Navigator.navigateToInviteMembersActivity(context);
        } else {
            loading.setValue(false);
            Log.e("CreateGroupModel", "Failed to create group", task.getException());
            Toast.makeText(context, "Failed to create group: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
