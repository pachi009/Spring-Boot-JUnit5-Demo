package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepo) {
    	return args -> {
    		Student prash = new Student("prashanth", "prash@gmail.com", LocalDate.of(1988, 6, 9));
    		Student suma = new Student("suma", "suma@gmail.com", LocalDate.of(1989, 8, 4));
    		studentRepo.saveAll(List.of(prash, suma));
    	};
	}
}
