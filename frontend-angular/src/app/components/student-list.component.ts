
import { Component, OnInit } from '@angular/core';
import { StudentService } from '../services/student.service';
import { StandardResponse } from '../models/standard-response.model';
import { Student } from '../models/student.model';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AddStudentComponent } from './add-student.component';  // Import AddStudentComponent
import { SearchStudentComponent } from './search-student.component';


@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule, AddStudentComponent ,SearchStudentComponent ]
})
export class StudentListComponent implements OnInit {
  students: Student[] = [];
  editingStudentId: string | null = null;
  editedStudent: Student = { id: '', firstName: '', lastName: '', email: '' };
  message: string = '';
  showAddStudentForm: boolean = false;  

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.getStudents();
  }

  onStudentAdded(){
    this.showAddStudentForm = ! this.showAddStudentForm;
    this.getStudents();
  }

  getStudents() {
    this.studentService.getStudents().subscribe({
      next: (res: StandardResponse) => {
        if (res.code === 200) {
          this.students = res.data;
        } else {
          this.message = 'No students found.';
        }
      },
      error: (err) => {
        this.message = 'Failed to load students.';
        console.error(err);
      }
    });
  }

  startEdit(student: Student) {
    this.editingStudentId = student.id;
    this.editedStudent = { ...student }; 
  }

  cancelEdit() {
    this.editingStudentId = null;
  }

  saveEdit() {
    if (this.editedStudent.firstName && this.editedStudent.lastName && this.editedStudent.email) {
      this.studentService.updateStudent(this.editedStudent).subscribe({
        next: (res: StandardResponse) => {
          this.message = 'Student updated successfully';
          this.getStudents();
          this.editingStudentId = null;
        },
        error: (err) => {
          this.message = 'Failed to update student.';
          console.error(err);
        }
      });
    } else {
      this.message = 'Please fill in all fields correctly.';
    }
  }

  deleteStudent(id: string) {
    if (confirm('Are you sure you want to delete this student?')) {
      this.studentService.deleteStudent(id).subscribe({
        next: (res: StandardResponse) => {
          this.message = res.message || 'Student deleted successfully';
          this.getStudents();
        },
        error: (err) => {
          this.message = 'Failed to delete student.';
          console.error(err);
        }
      });
    }
  }
}
