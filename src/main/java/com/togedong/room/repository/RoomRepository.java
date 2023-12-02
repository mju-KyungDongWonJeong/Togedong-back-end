package com.togedong.room.repository;

import com.togedong.record.Exercise;
import com.togedong.room.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("select r from Room r where r.managerName like '%' + ?1 + '%'"
        + "or r.title like '%' + ?1 + '%'"
        + "or r.exercise = ?2")
    List<Room> findByManagerNameLikeAndExercise(final String search, final Exercise exercise);
}
