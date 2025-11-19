package com.SCMS.SCMS.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "major")
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Long majorID;

    @Column(name = "major_name", length = 100, nullable = false)
    private String majorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(mappedBy = "majors", fetch = FetchType.LAZY)
    private Set<StudentMangement> students = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(majorID); 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Major))
            return false;
        Major other = (Major) obj;
        return Objects.equals(this.majorID, other.majorID);
    }
}
