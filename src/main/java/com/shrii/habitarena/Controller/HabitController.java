package com.shrii.habitarena.Controller;

import com.shrii.habitarena.DTO.HabitResponse;
import com.shrii.habitarena.Entity.Habit;
import com.shrii.habitarena.Service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Habit") public class HabitController {
    @Autowired
    HabitService habitService;

    @PostMapping
    public Habit  createHabit(@RequestBody Habit habit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return habitService.saveHabit(habit, username);
    }
    @GetMapping
    public List<HabitResponse> getHabit(){
        return habitService.getHabitsByUser(); //handles username
    }

    @DeleteMapping("/{id}")
    public boolean deleteHabit(@PathVariable Long id){

        System.out.println("DELETE endpoint reached. ID =" + id);
        return habitService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody Habit habit){
        return habitService.updateHabit(id, habit);
    }

    @PostMapping("/{id}/complete")
    public String completeHabit(@PathVariable Long id){
        habitService.markHabitDone(id);
        return "Habit marked as done";
    }
}
