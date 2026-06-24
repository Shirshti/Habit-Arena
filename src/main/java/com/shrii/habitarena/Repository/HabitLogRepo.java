package com.shrii.habitarena.Repository;

import com.shrii.habitarena.Entity.Habit;
import com.shrii.habitarena.Entity.HabitLog;
import com.shrii.habitarena.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitLogRepo extends JpaRepository<HabitLog, Long> {
    HabitLog findByHabitAndUserAndDate(Habit habit, User user, LocalDate date);
    List<HabitLog> findByHabitAndUserOrderByDateDesc(Habit habit, User user);
}
