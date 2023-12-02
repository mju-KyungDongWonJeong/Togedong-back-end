package com.togedong.room.repository;

import com.togedong.record.Exercise;
import com.togedong.room.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,String> {

    List<Room> findByManagerNameLikeAndExercise(final String managerName, final Exercise exercise);
}
