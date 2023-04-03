package nl.tue.besportive.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.squareup.picasso.Picasso;

import nl.tue.besportive.R;
import nl.tue.besportive.data.SportiveUser;
import nl.tue.besportive.repositories.UserRepository;

public class ProfilePageViewModel extends ViewModel {
    private final UserRepository userRepository;

    public ProfilePageViewModel(){
        userRepository = new UserRepository();
    }
    public LiveData<SportiveUser> getUser(){
        return userRepository.getLiveUser();
    }
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .into(view);
    }
}
