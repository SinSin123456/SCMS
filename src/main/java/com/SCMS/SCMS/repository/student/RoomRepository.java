package com.SCMS.SCMS.repository.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long > {

    Optional<Room> findByRoomName(String roomName);
} 