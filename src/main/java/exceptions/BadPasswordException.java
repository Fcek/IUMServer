package exceptions;

public class BadPasswordException extends Exception {
	public BadPasswordException(String msg) {
		super(msg);
	}
}
