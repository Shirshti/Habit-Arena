package com.shrii.habitarena.Entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(
            mappedBy = "habit",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<HabitLog> habitLogs = new ArrayList<>();

    @ManyToOne   //joining habit and user by relation
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser(){return user;}
    public void setUser(User user){
        this.user = user;
    }

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

    public List<HabitLog> getHabitLogs(){ return  habitLogs;}
    public void setHabitLogs(List<HabitLog> habitLogs) {
        this.habitLogs = habitLogs;
    }
}
