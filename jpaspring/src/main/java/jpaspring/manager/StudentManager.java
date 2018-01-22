package jpaspring.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jpaspring.model.Student;

public class StudentManager {

	private Map<Integer, Student> students = new HashMap<Integer, Student>(); // variável e construtor do objeto students do tipo Map
	
	public void save(Student student, int counter) {
		
		// nesse caso está verificando se a chave da qual se está tentando incluir na lista 
		// não existe e se a diferença de contagem da lista e do contador não indica uma recente exclusão na lista
		if((!existKey((int) student.getId())) && !(totalItens() < counter)) { 
			
				students.put((totalItens() + 1), student); 
			
			} else if((!existKey((int) student.getId())) && (totalItens() < counter)) {
				
				students.put((int) student.getId(), student); 
				
			}

		}
	falta tratar melhor o contador geral e fazer com ele se atualize de acordo com o checador de Keys existentes ou não
	public void delete(int Key) {
		
		
		students.remove(Key);
		
		// adicionar verificador antes de excluir, com exception também
	}
	
	public void update(int Key, Student student) {
		student.setId((long) Key); // setando ID para efetuar edição em seguida
		
		if(existKey(Key)) {
			students.put(Key, student); // recolocando novo objeto na mesma posição
		}
		
	}
	
	
	public Collection<Student> listAll(){
		
		return students.values();  // retorno da lista contendo objetos do tipo Student
	
	}
	
	public Student viewByKey(int Key) {
		
		if(existKey(Key)) {
			return students.get(Key); // retornando o item de lista, após filtrar por ID
		}else {
			return null;
		}
		
	
	}
	
	public List<String> search(String filter, int counterList){
		
		Student student = new Student(); // criando objeto para trabalharmos com cada objeto da lista Map
		
		// variável para armazenar os resultados
		List<String> result = new ArrayList<String>();
		
		if(listIsEmpty()) {
			
			result.add("Lista vazia"); // adicionando a lista notificação que a lista está vazia
			
		} else {
			
			// laço para percorrer todos os itens incluidos na lista até aqui
			for(int i = 1; i <= counterList; i++) {
				
				try { // usando try...catch pra prevenir erro de item vazio na lista, por causa das exclusões
					
					student = students.get(i); // obtendo objeto individualmente para posterior comparação do For
					
					if(student.getName().contains(filter)) { //verificação se o valor name do objeto corresponde ao valor do parametro informado
						
						result.add("Encontrado '" + filter + "' em: " + i); // informando resultado positivo da busca
					
					} else {
						
						result.add("Não encontrado em " + i); // informando resultado negativo da busca
						
					}
					
				} catch (NullPointerException ne) { // lançando exceção por causa de item vazio na lista 
					
					result.add("ÍNDICE " + i + " EXCLUÍDO"); // adicionando à lista que um item está excluido
					
				} // término do bloco catch

			} // final do laço For que varre todos os índices da lista
			
		}
		result.add("Total contador: " + counterList);
		result.add("total itens obtido da lista: " + totalItens());
		
			for(int x = 1; x <= counterList; x++) {
				result.add("key" + x + " (existe): " + existKey(x));
			}
		
		return result; // retorno dos resultados obtidos até aqui nas buscas da lista
		
	}
	
	
	public boolean existKey(int Key) {
		
		if(students.containsKey(Key)) { // verifica se existe item naquele ID passado
			return true;
		} else {
			return false;
		} // final da verificação se existe ID naquela lista
		
	}
	
	public boolean listIsEmpty() {
		if(students.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public int totalItens() {
		return students.size();
	}
	
}
