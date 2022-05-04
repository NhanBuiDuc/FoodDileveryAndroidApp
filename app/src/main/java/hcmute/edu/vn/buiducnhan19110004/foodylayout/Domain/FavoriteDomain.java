package hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain;

public class FavoriteDomain {
    private String user_email;
    private String food_id;

    public FavoriteDomain(String user_email, String food_id) {
        this.user_email = user_email;
        this.food_id = food_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }
}
