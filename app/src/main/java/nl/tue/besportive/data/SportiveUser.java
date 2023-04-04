package nl.tue.besportive.data;

public class SportiveUser {
    private String name;
    private String photoUrl;
    private int totalChallenges;
    private int totalTime;

    public SportiveUser() {}

    public SportiveUser(String name, String photoUrl, int totalChallenges, int totalTime) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.totalChallenges = totalChallenges;
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getTotalChallenges() {
        return totalChallenges;
    }

    public void setTotalChallenges(int totalChallenges) {
        this.totalChallenges = totalChallenges;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
