package nl.tue.besportive.data;

import java.util.Date;

public class CompletedChallenge {
    private String id;
    private String challengeId;
    private String userId;
    private Date completedAt;
    private int duration;

    private String name;
    private int difficulty;

    private String photoUrl;

    public CompletedChallenge() {
    }

    public CompletedChallenge(Challenge challenge, ActiveChallenge activeChallenge) {
        this.challengeId = challenge.getId();
        this.name = challenge.getName();
        this.difficulty = challenge.getDifficulty();
        this.completedAt = new Date();
        this.duration = this.completedAt.getSeconds() - activeChallenge.getStartedAt().getSeconds();
        this.userId = activeChallenge.getUserId();
        this.photoUrl = activeChallenge.getPhotoUrl();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public int getDuration() {
        return duration;
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

    public Difficulty getSmartDifficulty() {
        return Difficulty.getDifficulty(difficulty);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
