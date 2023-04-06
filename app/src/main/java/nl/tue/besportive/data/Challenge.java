package nl.tue.besportive.data;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import nl.tue.besportive.utils.FirebaseDocumentLiveData;

public class Challenge {
    @Exclude
    private String id;
    private String name;
    private int difficulty;


    public Challenge() {
    }

    public Challenge(String name, @NonNull Difficulty difficulty) {
        this.id = FirebaseDocumentLiveData.generateId();
        this.name = name;
        this.difficulty = difficulty.ordinal();
    }

    public Challenge(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getSmartDifficulty() {
        return Difficulty.getDifficulty(difficulty);
    }
}
