package exceptions;

public class ColumnNumberNotAllowedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ColumnNumberNotAllowedException() {
		super("The column number entered is not allowed");
	}

}
