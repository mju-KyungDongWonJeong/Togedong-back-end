package com.togedong.member.controller;

import com.togedong.member.controller.dto.RecordResponse;
import com.togedong.member.controller.dto.TotalChallengeResponse;
import com.togedong.global.annotation.TokenInfo;
import com.togedong.global.response.ResponseHandler;
import com.togedong.member.controller.dto.DashBoardResponse;
import com.togedong.member.entity.Member;
import com.togedong.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/dash-board")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("{userName}")
    public ResponseEntity<Object> getDashBoard(@PathVariable String userName,
        @TokenInfo Member member) {
        DashBoardResponse userDashBoard = memberService.getUserDashBoard(userName, member);
        return ResponseHandler.generateResponseWithoutMessage(HttpStatus.ACCEPTED, userDashBoard);
    }

    @PostMapping("/challenge/{userName}/{challengeName}")
    public ResponseEntity<Object> applyChallenge(@TokenInfo Member member,
        @PathVariable String userName, @PathVariable String challengeName) {
        memberService.participantChallenge(member, userName, challengeName);
        return ResponseHandler.generateResponseWithoutData(HttpStatus.CREATED, "챌린지 참여가 완료되었습니다!");
    }

    @GetMapping("/challenge/{userName}")
    public ResponseEntity<Object> getChallenges(@TokenInfo Member member,
        @PathVariable String userName) {
        TotalChallengeResponse challenges = memberService.getMemberChallenges(member,
            userName);
        return ResponseHandler.generateResponseWithoutMessage(HttpStatus.OK, challenges);
    }

    @GetMapping("/rank/{exerciseName}")
    public ResponseEntity<Object> getRank(@PathVariable String exerciseName) {
        List<RecordResponse> records = memberService.getExerciseRank(exerciseName);
        return ResponseHandler.generateResponseWithoutMessage(HttpStatus.OK, records);
    }
}
