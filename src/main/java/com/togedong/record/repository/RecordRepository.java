package com.togedong.record.repository;

import com.togedong.record.Exercise;
import com.togedong.record.entity.ExerciseRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<ExerciseRecord, Long> {

    List<ExerciseRecord> findByExercise(final Exercise exercise);
}
