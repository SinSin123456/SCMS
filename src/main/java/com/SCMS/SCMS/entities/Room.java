package com.SCMS.SCMS.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name", length = 50, nullable = false)
    private String roomName;

    @Column(name = "floor", length = 50)
    private String floor;
}
