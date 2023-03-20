package nl.tue.besportive;

public class Member {
    String name;
    String email;
    int image;

    int points;


    public Member(String name, String email, int image, int points) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.points = points;
    }
    public int getPoints(){
        return points;
    }
    public void setPoints(int points){
        this.points = points;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
