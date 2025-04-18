import { Component, EventEmitter, Output } from '@angular/core';
import { StudentService } from '../services/student.service';
import { Student } from '../models/student.model';
import { StandardResponse } from '../models/standard-response.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-student',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search-student.component.html',
})
export class SearchStudentComponent {
  studentId: string = '';
  searchedStudent: Student | null = null;
  message: string = '';
  editingStudentId: string | null = null;
  editedStudent: Student = { id: '', firstName: '', lastName: '', email: '' };
    showResult: boolean = false;

  constructor(private studentService: StudentService) {}

  searchStudent() {
    this.searchedStudent = null;
    this.message = '';
    if (!this.studentId.trim()) return;

    this.studentService.getStudentById(this.studentId).subscribe({
      next: (res: StandardResponse) => {
        this.searchedStudent = res.data;
        this.showResult=true;
      },
      error: (err) => {
        this.message = 'Student not found.';
        console.error(err);
      }
    });
  }

  ok(){
    this.showResult = false;
  }

  startEdit(student: Student) {
    this.editingStudentId = student.id;
    this.editedStudent = { ...student };
  }

  cancelEdit() {
    this.editingStudentId = null;
  }

  saveEdit() {
    this.studentService.updateStudent(this.editedStudent).subscribe({
      next: (res: StandardResponse) => {
        this.message = 'Student updated successfully.';
        this.searchedStudent = { ...this.editedStudent };
        this.cancelEdit();
      },
      error: (err) => {
        this.message = 'Failed to update student.';
        console.error(err);
      }
    });
  }

  deleteStudent(id: string) {
    if (confirm('Are you sure you want to delete this student?')) {
      this.studentService.deleteStudent(id).subscribe({
        next: () => {
          this.searchedStudent = null;
          this.message = 'Student deleted successfully.';
        },
        error: (err) => {
          this.message = 'Failed to delete student.';
          console.error(err);
        }
      });
    }
  }
}
