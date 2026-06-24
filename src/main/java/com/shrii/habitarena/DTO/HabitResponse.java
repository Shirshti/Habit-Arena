package com.shrii.habitarena.DTO;

public class HabitResponse {
    private Long id;
    private String title;
    private String description;
    private String username;

    //getterws and setters

    public Long getId(){return id;}
    public void setId(Long id){
        this.id = id;
    }
    public String getTitle(){return title;}
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){return description;}
    public void setDescription(String description){
        this.description = description;
    }
    public String getUsername(){return username;}
    public void setUsername(String username){
        this.username = username;
    }
}
