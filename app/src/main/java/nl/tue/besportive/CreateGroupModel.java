package nl.tue.besportive;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

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
    }
}
