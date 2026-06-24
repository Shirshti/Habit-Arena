package com.shrii.habitarena.Service;

import com.shrii.habitarena.DTO.HabitResponse;
import com.shrii.habitarena.Entity.Habit;
import com.shrii.habitarena.Entity.HabitLog;
import com.shrii.habitarena.Entity.User;
import com.shrii.habitarena.Exception.HabitNotFoundException;
import com.shrii.habitarena.Repository.HabitLogRepo;
import com.shrii.habitarena.Repository.HabitRepo;
import com.shrii.habitarena.Repository.UserRepo;
import com.shrii.habitarena.Util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {
    @Autowired
    private
    HabitRepo repo;

    @Autowired
    private
    UserRepo userRepo;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    HabitLogRepo habitLogRepo;

    public Habit saveHabit(Habit habit, String username){
        User user = userRepo.findByUsername(username);
        if (user == null ) {
            throw new RuntimeException("User not found");
        }

        habit.setUser(user);
        return repo.save(habit);
    }
    public List<HabitResponse> getAll(){

        List<Habit> habits = repo.findAll();
        List<HabitResponse> responseList = new ArrayList<>();
        for(Habit habit: habits){
            HabitResponse res = new HabitResponse();
            res.setId(habit.getId());
            res.setTitle(habit.getTitle());
            res.setDescription(habit.getDescription());


            if(habit.getUser()!= null){
                res.setUsername(habit.getUser().getUsername());
            }

            responseList.add(res);
        }
        return responseList;
    }
    public boolean deleteById(Long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }
    public Habit updateHabit(Long id, Habit habit){
      Habit old = repo.findById(id).orElseThrow(()-> new HabitNotFoundException("Habit not found with this id"));
      old.setDescription(habit.getDescription());
      old.setTitle(habit.getTitle());
      return repo.save(old);
    }

    public List<HabitResponse> getHabitsByUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        List<Habit> habits = repo.findByUser(user);
        List<HabitResponse> responseList = new ArrayList<>();
        for(Habit habit: habits){
            HabitResponse res = new HabitResponse();
            res.setId(habit.getId());
            res.setTitle(habit.getTitle());
            res.setDescription(habit.getDescription());
            res.setUsername(user.getUsername());

            responseList.add(res);
        }
        return responseList;
    }

    public void markHabitDone(Long habitId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Habit habit = repo.findByIdAndUser(habitId,user);
        if(habit == null) {
            throw new RuntimeException("Habit not found");
        }

        LocalDate today = LocalDate.now();

        HabitLog existingLog = habitLogRepo.findByHabitAndUserAndDate(habit,user,today);

        if(existingLog!= null){
            return; //already marked
        }

        HabitLog log = new HabitLog();
        log.setHabit(habit);
        log.setUser(user);
        log.setDate(today);
        log.setCompleted(true);

        habitLogRepo.save(log);
    }

}
