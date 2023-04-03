package nl.tue.besportive.data;

public class Challenge {
    private int difficulty;
    private String name;

    private String id;

    public Challenge() {

    }

    public Challenge(int difficulty, String name, String id) {
        this.difficulty = difficulty;
        this.name = name;
        this.id = id;

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

    @Override
    public String toString() {
        return "Challenges{" +
                "difficulty=" + difficulty +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Difficulty getSmartDifficulty() {
        return Difficulty.getDifficulty(difficulty);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

   
}
