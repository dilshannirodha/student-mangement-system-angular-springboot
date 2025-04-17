package com.springboot.student_management_system.service.impl;

import com.springboot.student_management_system.dto.StudentDTO;
import com.springboot.student_management_system.dto.request.StudentSaveRequestDTO;
import com.springboot.student_management_system.entity.Student;
import com.springboot.student_management_system.repository.StudentRepo;
import com.springboot.student_management_system.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceIMPL implements StudentService {

    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    public StudentServiceIMPL(StudentRepo studentRepo, ModelMapper modelMapper) {
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String studentCreate(StudentSaveRequestDTO studentSaveRequestDTO) {
        Student student = modelMapper.map(studentSaveRequestDTO, Student.class);

        if (studentRepo.existsByEmailEquals(student.getEmail())) {
            return "email already exists!";
        } else {
            studentRepo.save(student);
            return student.getFirstName() + " " + student.getLastName() + " saved";
        }
    }
}
