package com.springboot.student_management_system.controller;

import com.springboot.student_management_system.dto.StudentDTO;
import com.springboot.student_management_system.dto.request.StudentSaveRequestDTO;
import com.springboot.student_management_system.dto.request.StudentUpdateRequestDTO;
import com.springboot.student_management_system.service.StudentService;
import com.springboot.student_management_system.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path= {"/api/student"})
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = {"/get-all"})
    public ResponseEntity<StandardResponse> listStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return new ResponseEntity<>(
                    new StandardResponse(204, "No content available", null),
                    HttpStatus.NO_CONTENT
            );
        }
        return new ResponseEntity<>(
                new StandardResponse(200, "Students retrieved successfully", students),
                HttpStatus.OK
        );
    }

    @PostMapping(path = {"/create"})
    public ResponseEntity<StandardResponse> createStudent(@RequestBody StudentSaveRequestDTO studentSaveRequestDTO) {
        String created = studentService.studentCreate(studentSaveRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(201, " successfully saved", created),
                HttpStatus.CREATED);
    }


    @PutMapping(path = {"/update"})
    public ResponseEntity<StandardResponse> updateStudent(@RequestBody StudentUpdateRequestDTO studentUpdateRequestDTO) {
        String updated = studentService.studentUpdate(studentUpdateRequestDTO);
        return new ResponseEntity<>(
                new StandardResponse(200, "Student updated successfully", updated),
                HttpStatus.OK
        );
    }


    @DeleteMapping(path = {"/delete"}, params = {"id"})
    public ResponseEntity<StandardResponse> deleteStudent(@RequestParam(value = "id") String id) {
        String deleted = studentService.studentDelete(id);
        if (deleted.equals("Student not found")) {
            return new ResponseEntity<>(
                    new StandardResponse(404, "Student not found", null),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                new StandardResponse(200, "Student deleted successfully", deleted),
                HttpStatus.OK
        );
    }


    @GetMapping(path = {"/get-student"}, params = {"id"})
    public ResponseEntity<StandardResponse> getStudent(@RequestParam(value = "id") String id) {
        StudentDTO student = studentService.studentGet(id);
        if (student == null) {
            return new ResponseEntity<>(
                    new StandardResponse(404, "Student not found", null),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                new StandardResponse(200, "Student retrieved successfully", student),
                HttpStatus.OK
        );
    }

}

