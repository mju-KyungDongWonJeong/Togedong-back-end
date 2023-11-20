package com.togedong.record.service;

import com.togedong.member.controller.dto.RecordResponse;
import com.togedong.member.entity.Member;
import com.togedong.record.Exercise;
import com.togedong.record.controller.dto.RecordRequest;
import java.util.List;

public interface RecordService {

    List<RecordResponse> getRankByExercise(final Exercise exercise);

    void addRecord(final Member member, final RecordRequest request);
}
