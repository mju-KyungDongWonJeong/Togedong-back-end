package com.togedong.record.service;

import com.togedong.member.controller.dto.RecordResponse;
import com.togedong.record.Exercise;
import com.togedong.record.entity.ExerciseRecord;
import com.togedong.record.repository.RecordRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultRecordService implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public List<RecordResponse> getRankByExercise(final Exercise exercise) {
        List<ExerciseRecord> ranks = recordRepository.findDistinctByExercise(exercise);
        ranks.sort(Comparator.comparing(ExerciseRecord::getRecord));

        return ranks.stream()
            .map(ExerciseRecord::toDto)
            .toList();
    }
}
