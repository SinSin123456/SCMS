package com.SCMS.SCMS.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.JoinColumn;

@Data
@Entity
@Table(name= "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "join_date")
    private Timestamp joinDate;

    @Column(name = "is_active", nullable = false)
    private int isActive = 1;

    // public void setJoinDate(Timestamp joinTimestamp) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setJoinDate'");
    // }

    // public void setRoles(String roles2) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setRoles'");
    // }

    // public void setJoinDate(String joinDate2) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setJoinDate'");
    // }
}

