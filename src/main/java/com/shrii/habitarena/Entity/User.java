package com.shrii.habitarena.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user") //joining user and habit by relation
    private List<Habit> habits = new ArrayList<>();

    @Column(unique = true)
    private String username;
    private String email;
    @JsonIgnore
    private String password; //now password will not appear in API response

    public Long getId(){return id;}
    public void setId(Long id){
        this.id = id;
    }
    public String getUsername(){return username;}
    public void setUsername(String username){
        this.username = username;
    }
    public String getEmail(){return email;}
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){return password;}
    public void setPassword(String password){
        this.password = password;
    }
}
