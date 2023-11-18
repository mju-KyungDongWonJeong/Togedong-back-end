package com.togedong.user.controller;

import com.togedong.global.annotation.TokenInfo;
import com.togedong.global.response.ResponseHandler;
import com.togedong.user.controller.dto.DashBoardResponse;
import com.togedong.user.entity.Member;
import com.togedong.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
