package com.ss.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.service.StudentService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SpringController {
	
	//CSRF- Cross-Site Request Forgery //required for put,post and delete
	
	
	private List<StudentService> students=new ArrayList<>(List.of(
			new StudentService(1,"karthik",100),
			new StudentService(2,"ravi",90)
			));
	
	@PostMapping("/AddStudents")
	public StudentService addStudents(@RequestBody StudentService student) {
		students.add(student);
		return student;
	}
	
	@GetMapping("/csrf")
	public CsrfToken getCSRF(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	
	@GetMapping("/students")
	public List<StudentService> getStudents() {
		return students;
	}
	
}
