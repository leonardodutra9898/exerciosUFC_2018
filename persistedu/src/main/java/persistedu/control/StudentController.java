package persistedu.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import persistedu.model.Student;
import persistedu.repository.StudentRepository;

// classe controladora Student
@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	private StudentRepository studentsRepository; // injeção de dependência do repository
	
	
	// método pra registrar Student na base
	@RequestMapping(method = RequestMethod.POST)
	public void addStudent(@RequestBody Student student) {
	
		studentsRepository.
			save(student); // utilizando método save advindo do JPA
		
	} // final do método addStudent

	
	// classe pra excluir registro Student na base
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void removeStudent(@PathVariable("id") Long id) {
		studentsRepository.
			delete(id); // utilizando método delete advindo do JPA
		
	} // final do método removeStudent
	
	
	// método que lista itens vindo da base - Student
	@RequestMapping(method = RequestMethod.GET)
	public List<Student> listStudents(){

		return studentsRepository.
				findAll();  // utilizando método findAll advindo do JPA
		
	} // final do método listStudents	
	
	
	// método que pra visualizar um registro por vez - obtido da base por ID
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Student viewStudent(@PathVariable("id") Long id) {
		
		return studentsRepository.
				findOne(id); // utilizando método findOne advindo do JPA
		
	} // final do método viewStudent
	
	
	// método usado pra alterar os dados no registro vindo da base
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)	
	public void editStudent(@RequestBody Student student, @PathVariable("id") Long id) {
		student.setId(id);
		studentsRepository.save(student); // utilizando método save/merge advindo do JPA
		
	} // final do método editStudent

} // final da classe StudentController
