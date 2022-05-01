package hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper;

public class CurrentUser {
    private static int user_id;
    private static String user_name;
    private static String password;
    private static String full_name;

    public CurrentUser() {
    }

    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int user_id) {
        CurrentUser.user_id = user_id;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        CurrentUser.user_name = user_name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CurrentUser.password = password;
    }

    public static String getFull_name() {
        return full_name;
    }

    public static void setFull_name(String full_name) {
        CurrentUser.full_name = full_name;
    }
}
