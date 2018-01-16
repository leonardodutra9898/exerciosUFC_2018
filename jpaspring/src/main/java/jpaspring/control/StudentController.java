package jpaspring.control;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jpaspring.model.Student;

// Classe controladora contendo cadastro(inclusão numa lista), atualização (modificação indexada na lista), exclusão (remoção de item da lista) e listagem, bem como visualização 
@RestController
@RequestMapping("/api/student")
public class StudentController {

	
	private List<Student> students = new ArrayList<Student>(); // criando lista de objetos do tipo Student
	
	
	// método que adiciona item à lista students
	@RequestMapping(method = RequestMethod.POST)
	public Student addStudent(@RequestBody Student student) {
		student.setId(students.size() + 1);
		students.add(student);
		
		return student;
		
	} // final do método addStudent
	
	
	// método que remove item da lista students
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public void removeStudent(@PathVariable("id") long id) {
		
		students.remove((int) id);
		
	} // final do método removeStudent
	
	
	// método que modifica item da lista, recebendo por parâmetro um código e este será usado como indexador de referência
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void editStudent(@RequestBody Student student, @PathVariable long id) {

		students.set((int) id, student);
		
	} // final do método editStudent
	
	
	// método que lista todos os itens da lista
	@RequestMapping(method = RequestMethod.GET)
	public List<Student> listStudents(){
		
		return students; // retorno da lista contendo objetos do tipo Student
		
	} // final do método listStudents
	
	
	// método permite visualização do item da lista
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Student viewStudent(@PathVariable long id) {
		
		return students.get((int) id);
		
	} // final do método viewStudent
	
} // final da classe StudentController
