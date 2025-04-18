package com.springboot.student_management_system.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentSaveRequestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
