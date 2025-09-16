package com.SCMS.SCMS.repository.assignsubject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.AssignSubject;
import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Subject;
import com.SCMS.SCMS.entities.Teacher;
import com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub;
import com.SCMS.SCMS.model.request.assignsubject.ResListAssignSub;

@Repository
public interface AssignSubjectRepository extends JpaRepository<AssignSubject, Long> {

    List<AssignSubject> findByStudent(StudentMangement student);

    List<AssignSubject> findByTeacher(Teacher teacher);

    List<AssignSubject> findBySubject(Subject subject);

    boolean existsByStudentAndSubject(StudentMangement student, Subject subject);

    List<AssignSubject> findByStatusTrue();

    @Query("SELECT a FROM AssignSubject a " +
            "WHERE a.status = true AND (" +
            "LOWER(a.student.fullName) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
            "LOWER(a.teacher.fullName) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
            "LOWER(a.subject.subjectName) LIKE LOWER(CONCAT('%', :searchValue, '%')))")
    List<AssignSubject> searchActiveByKeyword(@Param("searchValue") String searchValue);

    // ---- NEW: DTO projections for editAssignSub ----
    @Query("SELECT new com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub(" +
            "s.studentID, s.fullName, sub.subjectID, sub.subjectName, 'STUDENT', a.term) " +
            "FROM AssignSubject a " +
            "JOIN a.student s " +
            "JOIN a.subject sub " +
            "WHERE a.id = :id")
    Optional<ResEditAssignSub> findStudentAssignSubject(@Param("id") Long id);

    @Query("SELECT new com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub(" +
            "t.teacherID, t.fullName, sub.subjectID, sub.subjectName, 'TEACHER', a.term) " +
            "FROM AssignSubject a " +
            "JOIN a.teacher t " +
            "JOIN a.subject sub " +
            "WHERE a.id = :id")
    Optional<ResEditAssignSub> findTeacherAssignSubject(@Param("id") Long id);

}
