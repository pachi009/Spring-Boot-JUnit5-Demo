package com.example.demo.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepo;

    @Test
    public void testFindByEmail() {
        Student student1 = new Student("Prashanth", "prashanth@gmail.com", LocalDate.of(1988, 6, 9));
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentRepo.save(student1);
        String testEmail = "prashanth@gmail.com";
//        Mockito.when(studentRepo.findByEmail(Mockito.anyString())).thenReturn(studentList);
        Assertions.assertThat(studentRepo.findByEmail(testEmail)).isNotEmpty();
    }

    @Test
    public void testEmailDoesNotExist() {
        String testEmail = "prashanth@gmail.com";
        Assertions.assertThat(studentRepo.findByEmail(testEmail)).isNullOrEmpty();
    }
}
