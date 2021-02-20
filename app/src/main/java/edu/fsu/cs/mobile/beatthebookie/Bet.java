package edu.fsu.cs.mobile.beatthebookie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Bet {

    private String ID;
    private  String text;
    private Date TimeCreated;
    private String ValidUntil;
    private String Website;
    private double FinalOdds;
    private String Creator;
    private String CreatorID;
    private int votes;

    public Bet()
    {
        Website="TEEEEST";
        text="TEST";
        votes=0;
    }

    public Bet(String id,String text, double odds, String website, String creator,String Valid)
    {
        this.ID=id;
        this.text=text;
        this.FinalOdds=odds;
        this.Website=website;
        this.TimeCreated= Calendar.getInstance().getTime();
        this.votes=0;
        this.Creator=creator;
        this.ValidUntil=Valid;
    }

    public String getCreatorID() {
        return CreatorID;
    }

    public void setCreatorID(String creatorID) {
        CreatorID = creatorID;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getID() {
        return ID;
    }

    public Date getTimeCreated() {
        return TimeCreated;
    }

    public String getWebsite() {
        return Website;
    }

    public double getFinalOdds() {
        return FinalOdds;
    }

    public String getText() {
        return text;
    }

    public int getVotes() {
        return votes;
    }

    public String getValidUntil() {
        return ValidUntil;
    }

    public void setFinalOdds(double finalOdds) {
        FinalOdds = finalOdds;
    }

    public void setTimeCreated(Date timeCreated) {
        TimeCreated = timeCreated;
    }

    public void setValidUntil(String validUntil) {
        ValidUntil = validUntil;
    }

    public void setWebsite(String website) {
        Website = website;
    }



    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
