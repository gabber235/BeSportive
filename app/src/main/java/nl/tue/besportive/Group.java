package nl.tue.besportive;

import java.util.Map;

public class Group {
    private String id;
    private String name;
    private String admin;
    private String code;
    private Map<String, Member> members;

    static class Member {
        private String name;
        private String photoUrl;

        public Member() {
        }

        public String getName() {
            return name;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
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
