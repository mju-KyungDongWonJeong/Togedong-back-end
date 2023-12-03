package com.togedong.room.repository;

import com.togedong.record.Exercise;
import com.togedong.room.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("select r from Room r where r.exercise = ?2 "
        + "and ((r.managerName like concat('%',?1,'%')"
        + "or coalesce(r.managerName,'') like concat('%',?1,'%')) "
        + "or (r.title like concat('%',?1,'%') "
        + "or coalesce(r.title,'') like concat('%',?1,'%')))")
    List<Room> searchByManagerNameOrTitleLikeByExercise(final String search,
        final Exercise exercise);
}
