package nl.tue.besportive;

import java.util.Date;

public class CompletedChallenges {
    private String challenge;
    private Date completedAt;
    private int duration;
    private String userId;
    private int status;

    private String name;
    private String difficulty;

    public CompletedChallenges() {}

    public CompletedChallenges(String challenge, Date completedAt, int duration, String userId, int status) {
        this.challenge = challenge;
        this.completedAt = completedAt;
        this.duration = duration;
        this.userId = userId;
        this.status = status;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
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
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
