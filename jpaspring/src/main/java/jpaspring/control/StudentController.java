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
	
	private int cumulateCountStudents = 0;
	
	// método que adiciona item à lista students
	@RequestMapping(method = RequestMethod.POST)
	public void addStudent(@RequestBody Student student) {

			// verificação pré-inclusão na lista
			if(students.size() > 0) { // check se tem itens na lista
				
				students.put(((int) students.size() + 1), student); // continua adicionando itens na lista
				cumulateCountStudents += 1;
				
			} else {
				
				students.put((int) students.size() + 1, student); // adiciona o primeiro item quando a lista tá zerada
				cumulateCountStudents += 1;

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
	public List<String> searchStudent(@RequestParam("filterName") String filterName) {
		
		List<String> result = new ArrayList<String>(); // armazenando em lista resultado da pesquisa de cada objeto Student
		
		Student student = new Student(); // criando objeto para trabalharmos com cada objeto da lista Map
		
		// laço para varrer todos os itens da lista, visando procurar por parametro informado
		

		// verificando se a lista está vazia
		if(students.isEmpty()) {
			
			result.add("Lista vazia");
			
		} else {
			
			// laço para percorrer todos os itens incluidos na lista até aqui
			for(int i = 1; i <= cumulateCountStudents; i++) {
				
				try { // usando try...catch pra prevenir erro de item vazio na lista, por causa das exclusões
					
					student = students.get(i); // obtendo objeto individualmente para posterior comparação do For
					
					if(student.getName().contains(filterName)) { //verificação se o valor name do objeto corresponde ao valor do parametro informado
						
						// informando resultado positivo da busca
						result.add("Encontrado '" + filterName + "' em: " + i);
					
					} else {
						
						// informando resultado negativo da busca
						result.add("Não encontrado em " + i);
						
					}
					
				} catch (NullPointerException ne) { // lançando exceção por causa de item vazio na lista 
					
					result.add("Índice " + i + " excluído");
					
				}
				


			}

		}		
	
		return result;
	  
	} // final do método buscar
	
} // final da classe StudentController
