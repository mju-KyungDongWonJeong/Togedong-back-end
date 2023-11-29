package com.togedong.record.service;

import com.togedong.member.controller.dto.RecordResponse;
import com.togedong.member.entity.Member;
import com.togedong.participant.service.ParticipantService;
import com.togedong.record.Exercise;
import com.togedong.record.controller.dto.RecordRequest;
import com.togedong.record.entity.ExerciseRecord;
import com.togedong.record.repository.RecordRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DefaultRecordService implements RecordService {

    private final RecordRepository recordRepository;
    private final ParticipantService participantService;

    @Override
    public List<RecordResponse> getRankByExercise(final Exercise exercise) {
        List<ExerciseRecord> ranks = recordRepository.findByExercise(exercise);
        ranks.sort(Comparator.comparing(record -> -record.getRecord()));

        return ranks.stream()
            .map(ExerciseRecord::toDto)
            .toList();
    }

    @Override
    @Transactional
    public void addRecord(final Member member, final RecordRequest request) {
        Exercise exercise = Exercise.findExerciseByName(request.exerciseName());
        ExerciseRecord record = new ExerciseRecord(exercise, member, request.record());
        member.addRecord(record);

        participantService.checkParticipantArchive(member);
        recordRepository.save(record);
    }
}
