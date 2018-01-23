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
				
			  //System.out.print("se não existir a Key na lista repassada e o total de itens na lista é igual ao contador geral \n\n");
			
			} else if((!existKey((int) student.getId())) && 
					(totalItens() < counter) && 
						(student.getId() <= counter)) {
				
					// identificar a Key excluida
					
				
				students.put((int) student.getId(), student); 
				
					if(getCumulateCountStudents() > 0) {
						
						setCumulateCountStudents(-1);
						
					}
					
				
				//System.out.print("se a Key foi excluida na lista repassada e o total de itens na lista é menor que o contador geral. DEVE TER TIDO EXCLUSÃO\n\n");	
				
			} else {
				
				students.put((int) student.getId(), student); 
				
				//setCumulateCountStudents(1);
				
				//System.out.print("situação em que tem exclusões, mas se quer adicionar itens no final \n\n");
				
			}

		}
	
	//falta tratar melhor o contador geral e fazer com ele se atualize de acordo com o checador de Keys existentes ou não
	
	public void delete(int Key) {
		
		// verificação com Key existente		
		if(existKey(Key)) {
			
			// quando a Key a ser excluida é igual ao acumulador, indicando que é o ultimo item da lista
			if(Key == getCumulateCountStudents()) {
				
				// verificando também se o item anterior a este não está excluido e que necessite ser removido do contador
				if(existKey(Key - 1)) { 
					
					students.remove(Key);
					setCumulateCountStudents(-1);
					
				} else {
					
					students.remove(Key);
					students.remove(Key - 1);
					setCumulateCountStudents(-2);
					
				}
				
			} else { // quando a Key passada é menor que o acumulador, ou seja, item do meio da lista ou inicio
				
				students.remove(Key);
				
			}
			
		} else { // quando a Key já foi excluida, mas ficou espaço livre sob efeito do contador
			
			// quando ainda há itens na lista
			if(totalItens() > 0) {
				
				// quando a Key a ser excluida é igual ao acumulador, indicando que é o ultimo item da lista
				if(Key == getCumulateCountStudents()) {
					
					// verificando também se o item anterior a este não está excluido e que necessite ser removido do contador
					if(existKey(Key - 1)) { 
						
						students.remove(Key);
						setCumulateCountStudents(-1);
						
					} else {
						
						students.remove(Key);
						students.remove(Key - 1);
						setCumulateCountStudents(-2);
						
					}
					
				} else { // quando a Key passada é menor que o acumulador, ou seja, item do meio da lista ou inicio
					
					students.remove(Key);
					
				}
				
			} else { // quando não há itens na lista
				
				System.out.print("Sem itens a excluir");
				
			}
			
		}

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
				result.add("key" + x + " (existe) [" + obtainValueKey(x) + "]: " + existKey(x));
			}
		
		return result; // retorno dos resultados obtidos até aqui nas buscas da lista
		
	}
	
	public int getCumulateCountStudents() {
		return cumulateCountStudents;
	}


	public void setCumulateCountStudents(int cumulateCountStudents) {
		this.cumulateCountStudents += cumulateCountStudents;
	}	
	
	
	public boolean existKey(int Key) {
		
		if(students.containsKey(Key)) { // verifica se existe item naquele ID passado
			return true;
		} else {
			return false;
		} // final da verificação se existe ID naquela lista
		
	}
	
	
	public Student obtainValueKey(int Key) {
		
		if(students.containsKey(Key)) { // verifica se existe item naquele ID passado
			return students.get(Key);
		} else {
			return null;
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
