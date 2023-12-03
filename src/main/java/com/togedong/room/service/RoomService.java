package com.togedong.room.service;

import com.togedong.global.exception.CustomException;
import com.togedong.global.exception.ErrorCode;
import com.togedong.member.entity.Member;
import com.togedong.record.Exercise;
import com.togedong.room.controller.dto.JoinRoomRequest;
import com.togedong.room.controller.dto.JoinRoomResponse;
import com.togedong.room.controller.dto.RoomCreateRequest;
import com.togedong.room.controller.dto.RoomCreateResponse;
import com.togedong.room.controller.dto.RoomResponse;
import com.togedong.room.controller.dto.RoomsResponse;
import com.togedong.room.entity.Room;
import com.togedong.room.repository.RoomRepository;
import com.togedong.room.service.domain.OpenViduManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RoomService {

    private final OpenViduManager openViduManager;
    private final RoomRepository roomRepository;

    @Transactional
    public RoomCreateResponse createRoom(final RoomCreateRequest request, final Member member) {
        String sessionId = openViduManager.initializeSession();
        Exercise exercise = Exercise.findExerciseByName(request.exerciseName());
        Room room = Room.of(request, sessionId, member.getUserName(), exercise);
        roomRepository.save(room);

        String connectionToken = openViduManager.createConnection(sessionId);
        return room.toCreateDto(connectionToken);
    }

    public JoinRoomResponse joinRoom(final String sessionId, final JoinRoomRequest request) {
        Room room = findById(sessionId);
        if (!room.canJoin(request.roomPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        String connectionToken = openViduManager.createConnection(sessionId);
        return new JoinRoomResponse(connectionToken);
    }

    public Room findById(final String id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
    }

    public RoomsResponse getRooms(final String exerciseName, final String search) {
        List<RoomResponse> rooms = roomRepository.searchByManagerNameOrTitleLikeByExercise(
                search, Exercise.findExerciseByName(exerciseName))
            .stream()
            .map(Room::toCommonDto)
            .toList();
        return new RoomsResponse(rooms);
    }

    @Transactional
    public void deleteRoom(final String roomId) {
        roomRepository.deleteById(roomId);
    }
}
