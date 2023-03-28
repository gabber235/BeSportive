package nl.tue.besportive;

public class Challenges {
    private int difficulty;
    private String name;

    public Challenges(){

    }
    public Challenges(int difficulty, String name) {
        this.difficulty = difficulty;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Challenges{" +
                "difficulty=" + difficulty +
                ", name='" + name + '\'' +
                '}';
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
