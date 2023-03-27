package nl.tue.besportive.data;

public class User {

    private String name;
    private String email;
    private String photoUrl;
    private int totalChallenges;
    private int totalTime;

    public User() {
    }


    public User(String name, String email, String photoUrl, int totalChallenges, int totalTime) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
        this.totalChallenges = totalChallenges;
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getTotalChallenges() {
        return totalChallenges;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setTotalChallenges(int totalChallenges) {
        this.totalChallenges = totalChallenges;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
