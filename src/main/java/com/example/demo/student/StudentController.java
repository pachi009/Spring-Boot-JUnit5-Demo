package com.example.demo.student;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public record StudentController(StudentService studentService) {

	@GetMapping
	public List<Student> getStudent() {
		return studentService.getAllStudents();
	}
	
	@PostMapping
	public void save(@RequestBody Student student) {
		studentService.save(student);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
	}
	
	@PutMapping(path = "{studentId}")
	public void updateStudent(
			@PathVariable Long studentId, 
			@RequestParam String name, 
			@RequestParam String email) {
		studentService.updateStudent(studentId, name, email);
	}
}
