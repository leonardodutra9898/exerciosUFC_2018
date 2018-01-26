package jpaspring.control;


import java.util.Collection;
import java.util.List;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jpaspring.manager.StudentManager;
import jpaspring.model.Student;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	StudentManager studentManager = new StudentManager();


	@RequestMapping(method = RequestMethod.POST)
	public void addStudent(@RequestBody Student student) {
				
		studentManager.save(student);
		
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void removeStudent(@PathVariable("id") long id) {
		
		studentManager.delete(id);
		
	} 
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void editStudent(@RequestBody Student student, @PathVariable long id) {
		
		studentManager.update(id, student);
		
	} 
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Student> listStudents(){
		
		return studentManager.listAll();
		
	} 
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Student viewStudent(@PathVariable int id) {
		
		return studentManager.obtainValueKey(id); 
		
	} 
	
	
	@RequestMapping(value = "/", params = "name", method = RequestMethod.GET)
	public List<Student> searchStudent(@RequestParam("name") String name) {
		
		return studentManager.search(name);
	  
	} 
	
} 
