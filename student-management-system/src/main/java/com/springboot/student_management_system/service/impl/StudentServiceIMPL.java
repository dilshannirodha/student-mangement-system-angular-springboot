package com.springboot.student_management_system.service.impl;

import com.springboot.student_management_system.dto.StudentDTO;
import com.springboot.student_management_system.dto.request.StudentSaveRequestDTO;
import com.springboot.student_management_system.dto.request.StudentUpdateRequestDTO;
import com.springboot.student_management_system.entity.Student;
import com.springboot.student_management_system.repository.StudentRepo;
import com.springboot.student_management_system.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
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

    @Override
    public String studentUpdate(StudentUpdateRequestDTO studentUpdateRequestDTO){

        Optional<Student> existingStudentOpt = studentRepo.findById(studentUpdateRequestDTO.getId());
        if(existingStudentOpt.isPresent()){
            Student existingStudent = existingStudentOpt.get();
            modelMapper.map(studentUpdateRequestDTO, existingStudent);
            studentRepo.save(existingStudent);
            return "Student " + studentUpdateRequestDTO.getFirstName()+ " updated successfully.";

        }else{
            return "student not  found";
        }
    }

    @Override
    public String studentDelete(int id) {
        Optional<Student> studentOpt = studentRepo.findById(id);

        if(studentOpt.isPresent()){
            studentRepo.deleteById(id);
            String name = studentOpt.get().getFirstName();
            return name + " deleted successfully";
        }
        else{
            return "student with id not  found";
        }
    }

    @Override
    public StudentDTO studentGet(int id) {
        Optional<Student> existingStudentOpt = studentRepo.findById(id);
        if(existingStudentOpt.isPresent()){
            Student existingStudent = existingStudentOpt.get();
            StudentDTO stdDTO = new StudentDTO();
            modelMapper.map(existingStudent, stdDTO);
            return stdDTO;

        }else{
            return null;
        }
    }
}
