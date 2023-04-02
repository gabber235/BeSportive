package nl.tue.besportive;

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

    public static enum Difficulty {
        EASY("Easy", "#00FF00"),
        MEDIUM("Medium", "#FFFF00"),
        HARD("Hard", "#FF0000");

        private final String name;
        private final String color;

        Difficulty(String name, String color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }

        public static Difficulty getDifficulty(int difficulty) {
            Difficulty[] difficulties = Difficulty.values();
            return difficulties[difficulty % difficulties.length];
        }
    }
}
