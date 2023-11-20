package com.togedong.record.controller;

import com.togedong.global.annotation.TokenInfo;
import com.togedong.global.response.ResponseHandler;
import com.togedong.member.entity.Member;
import com.togedong.record.controller.dto.RecordRequest;
import com.togedong.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/record")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<Object> addRecord(@TokenInfo final Member member,
        @Valid @RequestBody final RecordRequest request) {
        recordService.addRecord(member, request);
        return ResponseHandler.generateResponseWithoutData(HttpStatus.CREATED, "운동 기록이 등록되었습니다!");
    }
}
