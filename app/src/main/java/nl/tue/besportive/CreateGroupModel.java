package nl.tue.besportive;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CreateGroupModel extends BaseObservable {
    private String name;
    private boolean loading;


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public void createGroup(Context context) {
        setLoading(true);
        FirebaseFunctions.getInstance().getHttpsCallable("createGroup")
                .withTimeout(10, TimeUnit.SECONDS)
                .call(name)
                .addOnCompleteListener(task -> onGroupCreated(context, task));
    }

    private void onGroupCreated(Context context, Task<HttpsCallableResult> task) {
        if (task.isSuccessful()) {
            Navigator.navigateToInviteMembersActivity(context);
        } else {
            setLoading(false);
            Log.e("CreateGroupModel", "Failed to create group", task.getException());
            Toast.makeText(context, "Failed to create group: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
