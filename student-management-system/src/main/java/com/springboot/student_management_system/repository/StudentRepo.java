package com.springboot.student_management_system.repository;


import com.springboot.student_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface StudentRepo extends JpaRepository<Student, String> {
    boolean existsByEmailEquals(String email);
}
