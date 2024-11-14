package com.example.demo.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// BDDMockito is a different way of writing tests. We can use either Mockito or it's subclass BDDMockito to return mocked instances.
// I personally prefer Mockito.when(<some internal method call>).thenReturn(<desired object or throw exception>)

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() {
        studentService = null;
    }

    @Test
    void getAllStudents() {
        List<Student> students = List.of(new Student("Ravi", "ravi@gmail.com", LocalDate.of(1988, 11, 11)),
                new Student("Srinivas", "srinivas@gmail.com", LocalDate.of(1978, 10, 3)));

        Mockito.when(studentRepository.findAll()).thenReturn(students);
        List<Student> studentResult = studentService.getAllStudents();
        Assertions.assertThatList(studentResult).hasSize(2);
        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
    }

    @Test
    void saveStudent() {
        Student student = new Student("Ravi", "ravi@gmail.com", LocalDate.of(1988, 11, 11));
        studentService.save(student);

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        Assertions.assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void throwExceptionWhenEmailTaken() {
        Student student = new Student("Ravi", "ravi@gmail.com", LocalDate.of(1988, 11, 11));
//        BDDMockito.given(studentRepository.existsByEmail(ArgumentMatchers.anyString())).willReturn(true);
        Mockito.when(studentRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThatThrownBy(() -> studentService.save(student))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Use unique email Id");
        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testDeleteStudent() {
        Long id = 10L;
//        BDDMockito.given(studentRepository.existsById(Mockito.anyLong())).willReturn(true);
        Mockito.when(studentRepository.existsById(Mockito.anyLong())).thenReturn(true);
        studentService.deleteStudent(id);
        Mockito.verify(studentRepository).deleteById(Mockito.anyLong());
    }

    @Test
    void testDeleteStudentThrowsException() {
        Long id = 50L;
//        BDDMockito.given(studentRepository.existsById(Mockito.anyLong())).willReturn(false);
        Mockito.when(studentRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThatThrownBy(() -> studentService.deleteStudent(id))
                .hasMessage("Student with Id " +id +" does not exist")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testFindStudentById(){
        Long id = 10L;
        Student kamala = new Student("Kamala", "kamala@gmail.com", LocalDate.of(1978, 10, 3));
        Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(kamala));
        Optional<Student> stu = studentService.findStudentById(id);
        if(stu.isPresent())
            Assertions.assertThat(stu.get().getEmail()).isEqualTo(kamala.getEmail());

        Mockito.verify(studentRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testUpdateStudent(){
        Student student = new Student("Srinivas", "srinivas@gmail.com", LocalDate.of(1978, 10, 3));
        Long id = 10L;
//        BDDMockito.given(studentRepository.findById(Mockito.anyLong())).willReturn(Optional.of(student));
        Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));

        studentService.updateStudent(id, "Raghava", "raghava@gmail.com");
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }

    @Test
    void testFindStudentsByEmail(){
        String email = "srinivas@gmail.com";
        Student student = new Student("Srinivas", "srinivas@gmail.com", LocalDate.of(1978, 10, 3));
        Mockito.when(studentRepository.findByEmail(Mockito.anyString())).thenReturn(List.of(student));
        List<Student> students = studentService.findStudentsByEmail(email);
        Assertions.assertThat(students).hasSize(1);
    }
}
