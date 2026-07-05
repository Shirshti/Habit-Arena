package com.shrii.habitarena.Service;

import com.shrii.habitarena.Entity.Habit;
import com.shrii.habitarena.Entity.HabitLog;
import com.shrii.habitarena.Entity.User;
import com.shrii.habitarena.Repository.HabitLogRepo;
import com.shrii.habitarena.Repository.HabitRepo;
import com.shrii.habitarena.Repository.UserRepo;
import com.shrii.habitarena.Util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitLogService {
    @Autowired
    HabitLogRepo habitLogRepo;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserRepo userRepo;

    @Autowired
    HabitRepo repo;

    public HabitLog markHabitDone(Long habitId, String username) {

        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Habit habit =repo.findById(habitId).orElseThrow(()-> new RuntimeException("Habit not found"));

        LocalDate today = LocalDate.now();

        HabitLog existing = habitLogRepo.findByHabitAndUserAndDate(habit,user,today);
        if(existing != null){
            existing.setCompleted(true);
            return habitLogRepo.save(existing);
        }
        else{
            HabitLog newLog = new HabitLog();
            newLog.setCompleted(true
            );
            newLog.setHabit(habit);
            newLog.setDate(today);
            newLog.setUser(user);

            return habitLogRepo.save(newLog);
        }
    }

    public int getStreak(Long habitId, String username){

        User user = userRepo.findByUsername(username);
        Habit habit = repo.findById(habitId).orElseThrow();

        List<HabitLog> logs = habitLogRepo
                .findByHabitAndUserOrderByDateDesc(habit, user);
        int streak = 0;
        LocalDate today = LocalDate.now();

        for(HabitLog log: logs){

            //stop if date doesn't match expected streak flow
            if(!log.getDate().equals(today)){
                break;
            }

            //count only if done
            if(log.isCompleted()){
                streak++;
                today= today.minusDays(1);
            }
            else{
                break;
            }
        }
        return streak;
    }

    public int calculateStreak(Long habitId, String username){

        User user = userRepo.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Habit habit = repo.findByIdAndUser(habitId,user);
        if(habit == null) {
            throw new RuntimeException("Habit not found or not yours");
        }

        List<HabitLog> logs = habitLogRepo.findByHabitAndUserOrderByDateDesc(habit, user);

        int streak = 0;
        LocalDate expectedDate = LocalDate.now();

        for(HabitLog log : logs){

            if(log.getDate().equals(expectedDate) && log.isCompleted()){
                streak++;
                expectedDate = expectedDate.minusDays(1);
            }
            else if(log.getDate().isBefore(expectedDate)){
                expectedDate = expectedDate.minusDays(1);

                if(log.getDate().equals(expectedDate) && log.isCompleted()){
                    streak++;
                    expectedDate = expectedDate.minusDays(1);
                }
                else{
                    break;
                }
            }
            else{
                break;
            }
        }
        return streak;
    }
}
