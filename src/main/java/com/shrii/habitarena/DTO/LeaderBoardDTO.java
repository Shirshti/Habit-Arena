package com.shrii.habitarena.DTO;

public class LeaderBoardDTO {

    private String username;
    private int streak;

    public LeaderBoardDTO(String username, int streak){
        this.username = username;
        this.streak = streak;
    }

    public String getUsername(){return username;}
    public int getStreak(){return streak;}
}
