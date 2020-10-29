package exceptions;

public class NumberMirrorsNotAllowedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumberMirrorsNotAllowedException() {
		super("The number of mirrors is not allowed, there must be at least one mirror");
	}

}
