package persistedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import persistedu.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
