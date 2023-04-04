package nl.tue.besportive.data;

import java.util.Date;

public class CompletedChallenge {
    private String id;
    private String challenge;
    private String userId;
    private Date completedAt;
    private Date startedAt;
    private int duration;

    private String name;
    private int difficulty;

    private String photoUrl;
    private int status;

    public CompletedChallenge() {
    }

    public CompletedChallenge(String challenge, String userId, Date startedAt, String name, int difficulty, int status) {
        this.challenge = challenge;
        this.userId = userId;
        this.startedAt = startedAt;
        this.name = name;
        this.difficulty = difficulty;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
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

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
