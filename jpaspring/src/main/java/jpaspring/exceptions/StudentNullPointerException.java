package jpaspring.exceptions;

public class StudentNullPointerException extends NullPointerException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public StudentNullPointerException(String msg) {
		super(msg);
		
		throw new StudentNullPointerException("ÍNDICE EXCLUÍDO");

	}

}
