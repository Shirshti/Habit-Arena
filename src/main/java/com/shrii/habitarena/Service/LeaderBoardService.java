package com.shrii.habitarena.Service;

import com.shrii.habitarena.DTO.LeaderBoardDTO;
import com.shrii.habitarena.Entity.Habit;
import com.shrii.habitarena.Entity.User;
import com.shrii.habitarena.Repository.HabitRepo;
import com.shrii.habitarena.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderBoardService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private HabitRepo habitRepo;
    @Autowired
    private HabitLogService habitLogService;

    public List<LeaderBoardDTO> getLeaderBoard(){
        List<User> users = userRepo.findAll();
        List<LeaderBoardDTO> leaderBoard = new ArrayList<>();

        for(User user : users) {
            List<Habit> habits = habitRepo.findByUser(user);
            int totalStreak = 0;


            for (Habit habit : habits) {
                totalStreak += habitLogService.calculateStreak(habit.getId(), user.getUsername());
            }
            leaderBoard.add(new LeaderBoardDTO(user.getUsername(), totalStreak));
        }
        leaderBoard.sort((a, b) -> b.getStreak() - a.getStreak());
        return leaderBoard.stream().limit(10).toList();
    }
}
