package com.shrii.habitarena.Controller;

import com.shrii.habitarena.DTO.StatusRequest;
import com.shrii.habitarena.Entity.HabitLog;
import com.shrii.habitarena.Service.HabitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Habit")
public class HabitLogController {
    @Autowired
    HabitLogService habitLogService;

    @PostMapping("/complete/{habitId}")
    public HabitLog markHabitDone(@PathVariable Long habitId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return habitLogService.markHabitDone(habitId, username);
    }

    @GetMapping("/streak/{habitId}")
    public int getStreak(@PathVariable long habitId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return habitLogService.getStreak(habitId, username);
    }

}
