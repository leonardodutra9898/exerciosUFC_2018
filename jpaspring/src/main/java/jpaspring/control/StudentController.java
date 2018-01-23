package jpaspring.control;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jpaspring.exceptions.StudentNullPointerException;
import jpaspring.manager.StudentManager;
import jpaspring.model.Student;

// Classe controladora contendo cadastro(inclusão numa lista), 
// atualização (modificação indexada na lista), filtro também
// exclusão (remoção de item da lista) e listagem, bem como visualização 
@RestController
@RequestMapping("/api/student")
public class StudentController {

	StudentManager studentManager = new StudentManager();

	// método que adiciona item à lista students
	@RequestMapping(method = RequestMethod.POST)
	public void addStudent(@RequestBody Student student) {
				
		studentManager.save(student, studentManager.getCumulateCountStudents()); // continua adicionando itens na lista
				
		studentManager.setCumulateCountStudents(+1); // adicionando valor no contador da lista
		
	} // final do método addStudent
	
	
	// método que remove item da lista students
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void removeStudent(@PathVariable("id") long id) {
		
		studentManager.delete((int) id); // remove item da lista com base no índice
		
		//setCumulateCountStudents(-1);
		
	} // final do método removeStudent
	
	
	// método que modifica item da lista, recebendo por parâmetro um código e 
	// este será usado como indexador de referência
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void editStudent(@RequestBody Student student, @PathVariable int id) {
		
		studentManager.update(id, student); // recolocando novo objeto na mesma posição
		
	} // final do método editStudent
	
	
	// método que lista todos os itens da lista
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Student> listStudents(){
		
		return studentManager.listAll(); // retorno da lista contendo objetos do tipo Student
		
	} // final do método listStudents
	
	
	// método permite visualização do item da lista
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Student viewStudent(@PathVariable int id) {
		
		return studentManager.obtainValueKey(id); // retornando o item de lista, após filtrar por ID
		
	} // final do método viewStudent
	
	
	// método permite buscar/filtrar item pelo atributo "name"
	//@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/", params = "filterName", method = RequestMethod.GET)
	public List<String> searchStudent(@RequestParam("filterName") String filterName) {
		
		return studentManager.search(filterName, studentManager.getCumulateCountStudents());
	  
	} // final do método searchStudent
	
	
	
} // final da classe StudentController
