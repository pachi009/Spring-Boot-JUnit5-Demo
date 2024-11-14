package com.example.demo.student;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public record StudentService(StudentRepository studentRepo) {

	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}

	public void save(Student student) {
		if (studentRepo.existsByEmail(student.getEmail()))
			throw new IllegalStateException("Use unique email Id");
		studentRepo.save(student);
	}

	public Optional<Student> findStudentById(Long id) {
		return studentRepo.findById(id);
	}

	public void deleteStudent(Long id) {
		if(!studentRepo.existsById(id))
			throw new IllegalArgumentException("Student with Id " +id +" does not exist");
		studentRepo.deleteById(id);
	}

	public void updateStudent(Long studentId, String name, String email) {
		Student student = studentRepo.findById(studentId).get();
		student.setName(name);
		student.setEmail(email);
		studentRepo.save(student);
	}

	public List<Student> findStudentsByEmail(String email) {
		return studentRepo.findByEmail(email);
	}

}
