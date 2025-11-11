package com.SCMS.SCMS.service.subject;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCMS.SCMS.entities.Year;
import com.SCMS.SCMS.model.subject.YearDto;
import com.SCMS.SCMS.repository.student.YearRepository;

@Service
public class YearService {
    
    @Autowired
    private YearRepository yearRepository;

    public List<YearDto> getAllYear() {
        List<Year> years = yearRepository.findAll();

        return years.stream()
                .map(year -> new YearDto(year.getYearID(), year.getYearName()))
                .collect(Collectors.toList());
    }

}
