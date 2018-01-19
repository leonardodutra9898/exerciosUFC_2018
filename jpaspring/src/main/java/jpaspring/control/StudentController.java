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

import jpaspring.model.Student;

// Classe controladora contendo cadastro(inclusão numa lista), 
// atualização (modificação indexada na lista), filtro também
// exclusão (remoção de item da lista) e listagem, bem como visualização 
@RestController
@RequestMapping("/api/student")
public class StudentController {
	 
	//private Collection<Student> listAllStudents = new ArrayList<Student>(); // variavel lista de objetos do tipo Student
	
	private Map<Integer, Student> students = new HashMap<Integer, Student>(); // variável e construtor do objeto students do tipo Map
	
	// método que adiciona item à lista students
	@RequestMapping(method = RequestMethod.POST)
	public void addStudent(@RequestBody Student student) {

			// verificação pré-inclusão na lista
			if(students.size() > 0) { // check se tem itens na lista
				
				System.out.print("total de itens antes do fluxo do IF - quando já tem itens: " + students.size() + "\n");
				
				students.put(((int) students.size() + 1), student); // continua adicionando itens na lista
				
				System.out.print("total de itens na lista até aqui: "+ students.size() + "\n ");
				System.out.print("cod repassado pelo usuario: "+(int) student.getId() + "\n");
				System.out.print("item atual da lista composta: " + students.keySet() + "\n ");
				
			} else {
				
				System.out.print("total de itens antes do fluxo do IF - quando é o 1º item: " + students.size() + "\n");
				
				students.put((int) students.size() + 1, student); // adiciona o primeiro item quando a lista tá zerada
				
				System.out.print("total de itens na lista até aqui (primeiro item): "+ students.size() + "\n ");
				System.out.print("cod repassado pelo usuario (primeiro item): "+(int) student.getId() + "\n ");
				System.out.print("item atual da lista composta (primeiro item): " + students.keySet() + "\n ");
			}
		
	} // final do método addStudent
	
	
	// método que remove item da lista students
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void removeStudent(@PathVariable("id") long id) {
		
		students.remove((int) id); // remove item da lista com base no índice
		
	} // final do método removeStudent
	
	
	// método que modifica item da lista, recebendo por parâmetro um código e 
	// este será usado como indexador de referência
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void editStudent(@RequestBody Student student, @PathVariable int id) {

		student.setId((long)id);
		
		if(students.containsKey(id)) {
			
			students.put(id, student); // recolocando novo objeto na mesma posição
		}
		
	} // final do método editStudent
	
	
	// método que lista todos os itens da lista
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Student> listStudents(){
		
		// retorno da lista contendo objetos do tipo Student
		return students.values();
		
	} // final do método listStudents
	
	
	// método permite visualização do item da lista
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Student viewStudent(@PathVariable int id) {
		
		return students.get(id);
		
	} // final do método viewStudent
	
	
	// método permite buscar/filtrar item pelo atributo "name"
	//@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/", params = "filterName", method = RequestMethod.GET)
	public List<String> buscar(@RequestParam("filterName") String filterName) {
		
		List<String> result = new ArrayList<String>(); // armazenando em lista resultado da pesquisa de cada objeto Student
		
		Student student = new Student(); // criando objeto para trabalharmos com cada objeto da lista Map
		
		// laço para varrer todos os itens da lista, visando procurar por parametro informado
		for(int i = 1; i <= students.size(); i++) {
			
			student = students.get(i); // setando objeto individualmente para posterior comparação do For
			
			if(student.getName().contains(filterName)) { //verificação se o valor name do objeto corresponde ao valor do parametro informado
				
				// informando resultado positivo da busca
				result.add("Encontrado fragmento de texto para " + filterName + " (name) no objeto de índice: " + i);
			
			} else {
			
				// informando resultado negativo da busca
				result.add("Nenhum fragmento de texto encontrato para " + filterName + " (name) no objeto de índice: " + i);
			
			}
			
		}
		
		return result;
	  
	} // final do método buscar
	
} // final da classe StudentController
