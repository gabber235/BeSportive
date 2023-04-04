package nl.tue.besportive.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompletedChallenge {
    private String id;
    private String challengeId;
    private String userId;
    private Date completedAt;
    private int duration;

    private String name;
    private int difficulty;

    private String photoUrl;

    private List<String> lovers;

    public CompletedChallenge() {
    }

    public CompletedChallenge(Challenge challenge, ActiveChallenge activeChallenge) {
        this.challengeId = challenge.getId();
        this.name = challenge.getName();
        this.difficulty = challenge.getDifficulty();
        this.completedAt = new Date();
        this.duration = this.completedAt.getSeconds() - activeChallenge.getStartedAt().getSeconds();
        if (this.duration < 0) {
            this.duration = 0;
        }
        this.userId = activeChallenge.getUserId();
        this.photoUrl = activeChallenge.getPhotoUrl();
        this.lovers = new ArrayList<>();
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    @Exclude
    public String getCompletedAtString() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return dateFormat.format(completedAt);
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public int getDuration() {
        return duration;
    }

    @Exclude
    public String getDurationString() {
        int hours = duration / 3600;
        int minutes = (duration % 3600) / 60;
        int seconds = duration % 60;
        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getDifficulty() {
        return difficulty;
    }

    @Exclude
    public Difficulty getSmartDifficulty() {
        return Difficulty.getDifficulty(difficulty);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getLovers() {
        if (lovers == null) {
            lovers = new ArrayList<>();
        }
        return lovers;
    }

    public void setLovers(List<String> lovers) {
        this.lovers = lovers;
    }

    public void toggleLove() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        if (isLovedBy(user.getUid())) {
            getLovers().remove(user.getUid());
        } else {
            getLovers().add(user.getUid());
        }
    }

    @Exclude
    public boolean isLovedBy(String lover) {
        return getLovers().contains(lover);
    }

    @Exclude
    public String getLoves() {
        return String.valueOf(getLovers().size());
    }

    @Exclude
    public boolean isLoved() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return false;
        }
        return isLovedBy(user.getUid());
    }
}
