package com.springboot.student_management_system.controller;

import com.springboot.student_management_system.dto.StudentDTO;
import com.springboot.student_management_system.dto.request.StudentSaveRequestDTO;
import com.springboot.student_management_system.dto.request.StudentUpdateRequestDTO;
import com.springboot.student_management_system.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path= {"/api/student"})
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping( path={"/get-all"})
    public List<StudentDTO> ListStudents(){
        List<StudentDTO> students = studentService.getAllStudents();
        return students;
    }

    @PostMapping( path = {"/create"})
    public String createStudent(@Valid  @RequestBody StudentSaveRequestDTO studentSaveRequestDTO, BindingResult result){
        String created = studentService.studentCreate(studentSaveRequestDTO);
        if(result.hasErrors()){
            return "has validation issues";
        }else {
            return created;
        }
    }

    @PutMapping(path = {"/update"})
    public String updatestudent(@Valid @RequestBody StudentUpdateRequestDTO studentUpdateRequestDTO, BindingResult result){
        String updated = studentService.studentUpdate(studentUpdateRequestDTO);
        if(result.hasErrors()){
            return "has validation issues";
        }else {
            return updated;
        }
    }

    @DeleteMapping(path = {"/delete"}, params = {"id"})
    public String deleteStudent(@RequestParam(value = "id") int id){
        String deleted = studentService.studentDelete(id);
        return deleted;
    }

    @GetMapping(path = {"/get-student"}, params = {"id"})
    public StudentDTO getStudent(@RequestParam(value = "id") int id){
        StudentDTO std = studentService.studentGet(id);
        return std;
    }
}
