package com.springboot.student_management_system.service;

import com.springboot.student_management_system.dto.StudentDTO;
import com.springboot.student_management_system.dto.request.StudentSaveRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<StudentDTO> getAllStudents();

    String studentCreate(StudentSaveRequestDTO studentSaveRequestDTO);
}
