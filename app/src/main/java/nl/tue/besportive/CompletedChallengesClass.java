package nl.tue.besportive;

public class CompletedChallengesClass {
    String name;
    String description;
    int image;

    String difficulty;
    String timeOfCompletion;



    public CompletedChallengesClass(String name, String description, int image, String timeOfCompletion, String difficulty) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.timeOfCompletion = timeOfCompletion;
        this.difficulty = difficulty;
    }

    public String getTimeOfCompletion(){
        return timeOfCompletion;
    }
    public void setPoints(String timeOfCompletion){
        this.timeOfCompletion = timeOfCompletion;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
