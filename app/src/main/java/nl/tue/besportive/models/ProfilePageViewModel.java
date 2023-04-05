package nl.tue.besportive.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import nl.tue.besportive.data.SportiveUser;
import nl.tue.besportive.repositories.UserRepository;

public class ProfilePageViewModel extends ViewModel {
    private final UserRepository userRepository;

    public ProfilePageViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<SportiveUser> getUser() {
        return userRepository.getLiveUser();
    }
}
