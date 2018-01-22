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
import jpaspring.model.Student;

// Classe controladora contendo cadastro(inclusão numa lista), 
// atualização (modificação indexada na lista), filtro também
// exclusão (remoção de item da lista) e listagem, bem como visualização 
@RestController
@RequestMapping("/api/student")
public class StudentController {
	 
	//private Collection<Student> listAllStudents = new ArrayList<Student>(); // variavel lista de objetos do tipo Student
	
	private Map<Integer, Student> students = new HashMap<Integer, Student>(); // variável e construtor do objeto students do tipo Map
	
	private int cumulateCountStudents = 0; // contador geral da lista
	
	// método que adiciona item à lista students
	@RequestMapping(method = RequestMethod.POST)
	public void addStudent(@RequestBody Student student) {

			// verificação pré-inclusão na lista
			if(students.size() > 0) { // check se tem itens na lista
				
				students.put(((int) students.size() + 1), student); // continua adicionando itens na lista
				
				cumulateCountStudents += 1; // adicionando valor no contador da lista
				
			} else {
				
				students.put((int) students.size() + 1, student); // adiciona o primeiro item quando a lista tá zerada
				
				cumulateCountStudents += 1; // adicionando valor no contador da lista

			} // final do bloco Else que verifica se existem itens a lista
		
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

		student.setId((long)id); // setando ID para efetuar edição em seguida
		
		if(students.containsKey(id)) { // verifica se existe item naquele ID passado
			
			students.put(id, student); // recolocando novo objeto na mesma posição
		
		} // final da verificação se existe ID naquela lista
		
	} // final do método editStudent
	
	
	// método que lista todos os itens da lista
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Student> listStudents(){
		
		return students.values(); // retorno da lista contendo objetos do tipo Student
		
	} // final do método listStudents
	
	
	// método permite visualização do item da lista
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Student viewStudent(@PathVariable int id) {
		
		return students.get(id); // retornando o item de lista, após filtrar por ID
		
	} // final do método viewStudent
	
	
	// método permite buscar/filtrar item pelo atributo "name"
	//@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/", params = "filterName", method = RequestMethod.GET)
	public List<String> searchStudent(@RequestParam("filterName") String filterName) {
		
		Student student = new Student(); // criando objeto para trabalharmos com cada objeto da lista Map
		
		// variável para armazenar os resultados
		List<String> result = new ArrayList<String>();

		// verificando se a lista está vazia
		if(students.isEmpty()) {
			
			result.add("Lista vazia"); // adicionando a lista notificação que a lista está vazia
			
		} else {
			
			// laço para percorrer todos os itens incluidos na lista até aqui
			for(int i = 1; i <= cumulateCountStudents; i++) {
				
				try { // usando try...catch pra prevenir erro de item vazio na lista, por causa das exclusões
					
					student = students.get(i); // obtendo objeto individualmente para posterior comparação do For
					
					if(student.getName().contains(filterName)) { //verificação se o valor name do objeto corresponde ao valor do parametro informado
						
						result.add("Encontrado '" + filterName + "' em: " + i); // informando resultado positivo da busca
					
					} else {
						
						result.add("Não encontrado em " + i); // informando resultado negativo da busca
						
					}
					
				} catch (NullPointerException ne) { // lançando exceção por causa de item vazio na lista 
					
					result.add("Índice " + i + " excluído"); // adicionando à lista que um item está excluido
					
				} // término do bloco catch

			} // final do laço For que varre todos os índices da lista

		} // final do bloco Else que continua considerando que a lista não está vazia		
	
		return result; // retorno dos resultados obtidos até aqui nas buscas da lista
	  
	} // final do método buscar
	
} // final da classe StudentController
