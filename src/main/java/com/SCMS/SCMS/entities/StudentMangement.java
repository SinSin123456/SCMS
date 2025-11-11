package com.SCMS.SCMS.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name="student")
public class StudentMangement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long studentID;

    @Column(name = "FullName", length = 100, nullable = false)
    private String fullName;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Sex", length = 10)
    private String sex;

    @Column(name = "registerDate")
    private Timestamp registerDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", referencedColumnName = "id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "YearID")
    private Year year;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Student_Major", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "major_id"))
    private Set<Major> major = new HashSet<>();

    @CreatedDate
    @Column(name = "CreatedAt", updatable = false)
    private Timestamp createdAt;

    @Column(name = "CreatedBy", length = 100, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @Column(name = "UpdatedBy", length = 100)
    private String updatedBy;

    @Column(name = "DeletedAt")
    private Timestamp deletedAt;

    @Column(name = "DeletedBy", length = 100)
    private String deletedBy;

    @Column(name = "Status")
    private Boolean status; 

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof StudentMangement))
            return false;
        StudentMangement other = (StudentMangement) obj;
        return studentID != null && studentID.equals(other.getStudentID());
    }

    @Override
    public int hashCode() {
        return studentID != null ? studentID.hashCode() : 0;
    }
}
