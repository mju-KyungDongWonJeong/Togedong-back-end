package com.togedong.record.repository;

import com.togedong.record.entity.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<ExerciseRecord, Long> {

}
