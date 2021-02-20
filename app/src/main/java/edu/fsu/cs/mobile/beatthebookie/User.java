package edu.fsu.cs.mobile.beatthebookie;

public class User
{
    private String ID;
    private String email;
    private int points;

    User()
    {

    }


    User(String ID, String email, int points)
    {
        this.ID=ID;
        this.email=email;
        this.points=points;
    }

    public String getID() {
        return ID;
    }

    public int getPoints() {
        return points;
    }

    public String getEmail() {
        return email;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
