package com.shrii.habitarena.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class HabitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private LocalDate date;
   private boolean completed;
   private LocalDateTime createdAt;

   @ManyToOne
   @JoinColumn(name = "habit_id")
   private Habit habit;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

    @PrePersist         //automatic timestamps
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId(){return id;}
    public void setId(Long id){
       this.id = id;
    }

    public Habit getHabit(){return habit;}
    public void setHabit(Habit habit){
       this.habit = habit;
    }
    public User getUser(){return user;}
    public void setUser(User user){
       this.user = user;
    }
    public LocalDate getDate(){return date;}
    public void setDate(LocalDate date){
       this.date = date;
    }
    public boolean isCompleted(){return completed;}
    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

}
