// src/app/services/student.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../models/student.model';
import { StandardResponse } from '../models/standard-response.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:8082/api/student';

  constructor(private http: HttpClient) {}

  getStudents(): Observable<StandardResponse> {
    return this.http.get<StandardResponse>(`${this.apiUrl}/get-all`);
  }

  createStudent(student: Student): Observable<StandardResponse> {
    return this.http.post<StandardResponse>(`${this.apiUrl}/create`, student);
  }

  getStudentById(id: string): Observable<StandardResponse> {
    return this.http.get<StandardResponse>(`${this.apiUrl}/get-student?id=${id}`);
  }

  updateStudent(student: Student): Observable<StandardResponse> {
    return this.http.put<StandardResponse>(`${this.apiUrl}/update`, student);
  }

  deleteStudent(id: string): Observable<StandardResponse> {
    return this.http.delete<StandardResponse>(`${this.apiUrl}/delete?id=${id}`);
  }
}
