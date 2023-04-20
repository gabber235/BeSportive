package nl.tue.besportive.data;


import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class ActiveChallenge {
    @Exclude
    private String id;
    private String challengeId;
    private String userId;
    private Date startedAt;
    private String photoUrl;


    public ActiveChallenge() {
    }

    public ActiveChallenge(String challengeId, String userId) {
        this.challengeId = challengeId;
        this.userId = userId;
        startedAt = new Date();
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
