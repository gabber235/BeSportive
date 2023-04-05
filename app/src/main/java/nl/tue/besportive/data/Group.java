package nl.tue.besportive.data;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class Group {
    @Exclude
    private String id;
    private String name;
    private String admin;
    private String code;
    private Map<String, Member> members;

    public static class Member implements Comparable<Member> {
        private String id;
        private String name;
        private String photoUrl;

        private int points;

        public Member() {
        }

        public Member(String id, String name, String photoUrl, int points) {
            this.id = id;
            this.name = name;
            this.photoUrl = photoUrl;
            this.points = points;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        @Override
        public int compareTo(Member o) {
            return Integer.compare(o.points, points);
        }
    }

    public Group() {
    }

    public Group(String id, String name, String admin, String code, Map<String, Member> members) {
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.code = code;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdmin() {
        return admin;
    }

    public String getCode() {
        return code;
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMembers(Map<String, Member> members) {
        this.members = members;
    }
}
