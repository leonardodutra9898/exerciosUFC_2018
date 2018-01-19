package jpaspring.model;


public class Student {

	private long id; // atributo id
	
	private String name; // atributo name
	
	private String email; // atributo email

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
} // final da classe Student
