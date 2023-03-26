package nl.tue.besportive;

import java.util.HashMap;

public class Member {
    String name;
    String email;
    int image;

    int points;
    String userId;

    public Member(String name, String email, int image, int points, String userId) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.points = points;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
