package com.togedong.room.controller;

import com.togedong.global.annotation.TokenInfo;
import com.togedong.global.response.ResponseHandler;
import com.togedong.member.entity.Member;
import com.togedong.room.controller.dto.JoinRoomRequest;
import com.togedong.room.controller.dto.JoinRoomResponse;
import com.togedong.room.controller.dto.RoomCreateRequest;
import com.togedong.room.controller.dto.RoomCreateResponse;
import com.togedong.room.controller.dto.RoomsResponse;
import com.togedong.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
@Slf4j
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Object> initializeSession(@TokenInfo final Member member,
        @Valid @RequestBody final RoomCreateRequest request) {
        RoomCreateResponse response = roomService.createRoom(request, member);
        return ResponseHandler.generateResponse("방이 생성되었습니다.", HttpStatus.CREATED, response);
    }

    @PostMapping("/{roomId}")
    public ResponseEntity<Object> joinRoom(@PathVariable("roomId") final String sessionId,
        @RequestBody(required = false) final JoinRoomRequest request) {
        JoinRoomResponse response = roomService.joinRoom(sessionId, request);
        return ResponseHandler.generateResponseWithoutMessage(HttpStatus.ACCEPTED, response);
    }

    @GetMapping("/{exerciseName}")
    public ResponseEntity<Object> getRooms(
        @PathVariable("exerciseName") final String exerciseName,
        @RequestParam(required = false) final String search) {
        RoomsResponse response = roomService.getRooms(exerciseName, search);
        return ResponseHandler.generateResponseWithoutMessage(HttpStatus.OK, response);
    }

}
