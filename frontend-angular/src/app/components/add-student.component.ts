// src/app/components/add-student/add-student.component.ts

import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StudentService } from '../services/student.service';
import { Student } from '../models/student.model';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-add-student',
  templateUrl: './add-student.component.html',
  standalone: true,
  imports: [CommonModule, HttpClientModule, ReactiveFormsModule]

})
export class AddStudentComponent {
  studentForm: FormGroup;
  message: string = '';

  constructor(private fb: FormBuilder, private studentService: StudentService) {
    this.studentForm = this.fb.group({
      id: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });
  }

  onSubmit() {
    if (this.studentForm.valid) {
      const newStudent: Student = this.studentForm.value;
      this.studentService.createStudent(newStudent).subscribe({
        next: (res) => {
          this.message = res.message || 'Student created successfully!';
          this.studentForm.reset();
        },
        error: (err) => {
          this.message = 'Failed to create student. ' + err.error?.message || 'Please try again.';
          console.error(err);
        }
      });
    } else {
      this.message = 'Please fill in all fields correctly.';
    }
  }
}
