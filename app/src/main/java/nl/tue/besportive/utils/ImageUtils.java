package nl.tue.besportive.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import nl.tue.besportive.R;

public class ImageUtils {
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        String url = imageUrl.replace("127.0.0.1", "10.0.2.2");

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(view);
    }
}
