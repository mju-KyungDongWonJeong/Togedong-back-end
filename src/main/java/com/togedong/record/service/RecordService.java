package com.togedong.record.service;

import com.togedong.member.controller.dto.RecordResponse;
import com.togedong.record.Exercise;
import java.util.List;

public interface RecordService {

    List<RecordResponse> getRankByExercise(final Exercise exercise);
}
