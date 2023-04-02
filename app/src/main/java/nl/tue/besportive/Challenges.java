package nl.tue.besportive;

public class Challenges {
    private int difficulty;
    private String name;

    private String id;
    public Challenges(){

    }
    public Challenges(int difficulty, String name, String id) {
        this.difficulty = difficulty;
        this.name = name;
        this.id =id;

    }
    public Challenges(String id) {
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
