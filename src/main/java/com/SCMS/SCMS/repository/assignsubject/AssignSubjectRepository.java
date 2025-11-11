package com.SCMS.SCMS.repository.assignsubject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.AssignSubject;
import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Major;
import com.SCMS.SCMS.entities.Teacher;
import com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub;

@Repository
public interface AssignSubjectRepository extends JpaRepository<AssignSubject, Long> {

        List<AssignSubject> findByStudent(StudentMangement student);

        List<AssignSubject> findByTeacher(Teacher teacher);

        
        List<AssignSubject> findByMajor(Major major);

      
        boolean existsByStudentAndMajor(StudentMangement student, Major major);

        List<AssignSubject> findByStatusTrue();

        
        @Query("SELECT a FROM AssignSubject a " +
                        "WHERE a.status = true AND (" +
                        "LOWER(a.student.fullName) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                        "LOWER(a.teacher.fullName) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
                        "LOWER(a.major.majorName) LIKE LOWER(CONCAT('%', :searchValue, '%')))")
        List<AssignSubject> searchActiveByKeyword(@Param("searchValue") String searchValue);

       
        @Query("SELECT new com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub(" +
                        "s.studentID, s.fullName, m.id, m.majorName, 'STUDENT', a.term) " +
                        "FROM AssignSubject a " +
                        "JOIN a.student s " +
                        "JOIN a.major m " +
                        "WHERE a.id = :id")
        Optional<ResEditAssignSub> findStudentAssignSubject(@Param("id") Long id);

      
        @Query("SELECT new com.SCMS.SCMS.model.request.assignsubject.ResEditAssignSub(" +
                        "t.teacherID, t.fullName, m.id, m.majorName, 'TEACHER', a.term) " +
                        "FROM AssignSubject a " +
                        "JOIN a.teacher t " +
                        "JOIN a.major m " +
                        "WHERE a.id = :id")
        Optional<ResEditAssignSub> findTeacherAssignSubject(@Param("id") Long id);

}
