package persistedu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistedu.model.Student;
import persistedu.repository.StudentRepository;

@Service
public class StudentsService {

	@Autowired
	private StudentRepository studentsRepository;
	
	public List<Student> listStudents(){
		return studentsRepository.
				findAll();
	}
	
}
