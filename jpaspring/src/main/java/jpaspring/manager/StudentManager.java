package jpaspring.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jpaspring.model.Student;

public class StudentManager {

	private Map<Long, Student> students = new HashMap<Long, Student>(); 
	
	private long counter = 0; // contador
	
	public void save(Student student) {
		
		if(!existKey(student.getId())) { 
			
				students.put(student.getId(), student); 
				setCounter(+1);
			
			} 

		} 
	
	
	public void delete(long key) {

		students.remove(key);
		setCounter(-1);
		
	} 
	
	
	public void update(long key, Student student) {
		
		student.setId(key); 
		
		if(existKey(key)) {
			
			students.put(key, student);
			
		}
		
	} 
	
	
	public Collection<Student> listAll(){
		
		return students.values(); 
	
	} 

	
	public List<Student> search(String filter){
		
		List<Student> resultStudents = new  ArrayList<>();
		
		
		for (Student s : students.values()) {
			
			if(s.getName().contains(filter))
				
				resultStudents.add(s);
			
		}
		
		return resultStudents;

	} 
	
	
	public long getCounter() {
		
		return counter;
	
	} 
	
	
	public void setCounter(long counter) {
		
		this.counter += counter;
	
	} 
	
	
	public boolean existKey(long key) {
		
		if(students.containsKey(key)) { 
			
			return true;
			
		} else {
			
			return false;
			
		} 
		
	}
	

	public Student obtainValueKey(long key) {
		
		if(students.containsKey(key)) {
			
			return students.get(key);
			
		} else {
			
			return null;
			
		} 
		
	} 	
	
}
