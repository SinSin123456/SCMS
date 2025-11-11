package com.SCMS.SCMS.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "year")
public class Year {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YearID")
    private Long yearID;

    @Column(name = "YearName")
    private String yearName;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentMangement> students = new ArrayList<>();
}
