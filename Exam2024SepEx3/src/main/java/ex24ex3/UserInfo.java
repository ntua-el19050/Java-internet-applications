package ex24ex3;

public class UserInfo {
    private int id;
    private int roleIndex;

    public UserInfo(int id, int roleIndex) {
        this.id = id;
        this.roleIndex = roleIndex;
    }

    public int getId() {
        return id;
    }

    public int getRoleIndex() {
        return roleIndex;
    }
}