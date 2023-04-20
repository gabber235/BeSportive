package nl.tue.besportive.data;

public enum Difficulty {
    EASY("Easy", 0xFF4CAF50),
    MEDIUM("Medium", 0xFFFF9800),
    HARD("Hard", 0xFFF44336);

    private final String name;
    private final int color;

    Difficulty(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public static Difficulty getDifficulty(int difficulty) {
        Difficulty[] difficulties = Difficulty.values();
        return difficulties[difficulty % difficulties.length];
    }
}