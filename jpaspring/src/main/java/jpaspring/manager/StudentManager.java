package jpaspring.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jpaspring.model.Student;

public class StudentManager {

	private Map<Integer, Student> students = new HashMap<Integer, Student>(); // variável e construtor do objeto students do tipo Map
	
	private int cumulateCountStudents = 0; // contador geral da lista
	
	public void save(Student student, int counter) {
		
		// nesse caso está verificando se a chave da qual se está tentando incluir na lista 
		// não existe e se a diferença de contagem da lista e do contador não indica uma recente exclusão na lista
		if((!existKey((int) student.getId())) && (totalItens() == counter)) { 
			
				students.put((totalItens() + 1), student); 
			
			} else if((!existKey((int) student.getId())) && 
					(totalItens() < counter) && 
						(student.getId() <= counter)) {
				
					students.put((int) student.getId(), student); 
				
					// verificando se acumulador não tá zerado
					if(getCumulateCountStudents() > 0) {
						
						setCumulateCountStudents(-1);
						
					} 
				
			} else { // adicionando novo item a lista
				
				students.put((int) student.getId(), student); 
				
			} // FINAL - adicionando novo item a lista

		} // final do método save
	
	
	public void delete(int Key) {
		
		// verificação com Key existente		
		if(existKey(Key)) {
			
			// quando a Key a ser excluida é igual ao acumulador, indicando que é o ultimo item da lista
			if(Key == getCumulateCountStudents()) {
				
				// verificando também se o item anterior a este não está excluido e que necessite ser removido do contador
				if(existKey(Key - 1)) { 
					
					students.remove(Key);
					setCumulateCountStudents(-1);
					
				} else { // removendo a Key atual e a anterior
					
					students.remove(Key);
					students.remove(Key - 1);
					setCumulateCountStudents(-2);
					
				} // FINAL - // removendo a Key atual e a anterior
				
			} else { // quando a Key passada é menor que o acumulador, ou seja, item do meio da lista ou inicio
				
				students.remove(Key);
				
			} // FINAL - quando a Key passada é menor que o acumulador, ou seja, item do meio da lista ou inicio
			
		} else { // quando a Key já foi excluida, mas ficou espaço livre sob efeito do contador
			
			// quando ainda há itens na lista
			if(totalItens() > 0) {
						
						// loop para ir verificando itens vazios e aplicar remoção
						do {
						
							students.remove(Key);
							setCumulateCountStudents(-1);
						
						}while(!existKey(Key-1));
				
			} else { // quando não há itens na lista
				
				System.out.print("Sem itens a excluir");
				
			} // FINAL - quando não há itens na lista
			
		} // FINAL - quando a Key já foi excluida, mas ficou espaço livre sob efeito do contador

	} // final do método delete
	
	
	// método para atualizar valores da lista
	public void update(int Key, Student student) {
		
		student.setId((long) Key); // setando ID para efetuar edição em seguida
		
		if(existKey(Key)) {
			students.put(Key, student); // recolocando novo objeto na mesma posição
		}
		
	} // final do método update
	
	
	// método para retornar todos os itens da lista
	public Collection<Student> listAll(){
		
		return students.values();  // retorno da lista contendo objetos do tipo Student
	
	} // final do método listAll
	

	
	// método para filtro/pesquisa na lista, através de parametro passado
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
			
		} // final do bloco else, quando a lista possui itens

		return result; // retorno dos resultados obtidos até aqui nas buscas da lista
		
// para auxiliar em testes		
//		result.add("Total contador: " + counterList);
//		result.add("total itens obtido da lista: " + totalItens());
		
//			for(int x = 1; x <= counterList; x++) {
//				result.add("key" + x + " (existe) [" + obtainValueKey(x) + "]: " + existKey(x));
//			}
	} // final do método search
	
	
	// método retorna contagem atual do acumulador auxiliar da lista
	public int getCumulateCountStudents() {
		
		return cumulateCountStudents;
	
	} // final do método getCumulateCountStudents


	// método para manipular valores do acumulador auxiliar da lista
	public void setCumulateCountStudents(int cumulateCountStudents) {
		
		this.cumulateCountStudents += cumulateCountStudents;
	
	} // final do método setCumulateCountStudents
	
	
	// método verifica se key repassada existe na lista
	public boolean existKey(int Key) {
		
		if(students.containsKey(Key)) { // verifica se existe item naquele ID passado
			
			return true;
			
		} else {
			
			return false;
			
		} // final da verificação se existe ID naquela lista
		
	} // final do método existKey
	
	
	// método para obter valor colocado naquela Key
	public Student obtainValueKey(int Key) {
		
		if(students.containsKey(Key)) { // verifica se existe item naquele ID passado
			
			return students.get(Key);
			
		} else {
			
			return null;
			
		} // final da verificação se existe ID naquela lista
		
	} // final do método obtainValueKey	
	
	
	// método para verificar se a lista está vazia
	public boolean listIsEmpty() {
		
		if(students.isEmpty()) {
			
			return true;
			
		}else {
			
			return false;
			
		}
		
	} // final do método listIsEmpty
	
	
	// método para retornar total de itens atualmente inseridos na lista
	public int totalItens() {
		
		return students.size();
		
	} // final do método totalItens
	
}
