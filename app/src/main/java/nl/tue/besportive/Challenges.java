package nl.tue.besportive;

public class Challenges {
    private int difficulty;
    private boolean distanceBased;
    private String name;
    private String challengeId;

    public Challenges() {
    }

    public Challenges(int difficulty, boolean distanceBased, String name) {
        this.difficulty = difficulty;
        this.distanceBased = distanceBased;
        this.name = name;
    }
    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDistanceBased() {
        return distanceBased;
    }

    public void setDistanceBased(boolean distanceBased) {
        this.distanceBased = distanceBased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
