package com.SCMS.SCMS.entities;

import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id") 
    private Long id;
    
    @Column(name = "subject_code", length = 50, nullable = false, unique = true)
    private String subjectCode;

    @Column(name = "subject_name", length = 100, nullable = false)
    private String subjectName;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;
}
