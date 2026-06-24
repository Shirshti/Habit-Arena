package com.shrii.habitarena.Repository;

import com.shrii.habitarena.Entity.Habit;
import com.shrii.habitarena.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepo extends JpaRepository<Habit,Long> {
    List<Habit> findByUser(User user);

    Habit findByIdAndUser(Long id, User user);
}

