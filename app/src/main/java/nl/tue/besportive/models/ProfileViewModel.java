package nl.tue.besportive.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nl.tue.besportive.data.User;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<User> user;

    public ProfileViewModel() {
        user = new MutableLiveData<>();
        user.setValue(new User("Laurens", "laurens.jonkers@gmail.com", "https://api.multiavatar.com/95aa24e1eb55052ad2.png", 69, 69));
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
