package com.example.supertmstest.controller;

import com.example.supertmstest.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static final List<Student> students = Arrays.asList(
            new Student(1, "Artyom"),
            new Student(2, "Daniil"),
            new Student(3, "Nastia"),
            new Student(2, "ne Danik"));

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Student findById(@PathVariable long id){
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow( () -> new RuntimeException(
                        "Student with " + id + " is not found :("
                ));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void addStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('student:write')")
    public void deleteStudent(@PathVariable long id){
        System.out.println("delete student with id: " + id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable long id){
        System.out.println("update student with id: " + id);
    }


}
