package nl.tue.besportive.data;

public enum Difficulty {
    EASY("Easy", 0x4CAF50),
    MEDIUM("Medium", 0xFF9800),
    HARD("Hard", 0xF44336);

    private final String name;
    private final int color;

    Difficulty(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    public int getColorInt() {
        return color;
    }

    public static Difficulty getDifficulty(int difficulty) {
        Difficulty[] difficulties = Difficulty.values();
        return difficulties[difficulty % difficulties.length];
    }
}