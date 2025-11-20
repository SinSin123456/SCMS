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


        List<AssignSubject> findByTeacher(Teacher teacher);

        
        List<AssignSubject> findByMajor(Major major);


        List<AssignSubject> findByStatusTrue();
        

}
