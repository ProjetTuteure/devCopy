package gpi.exception;

public class PrimaryKeyException extends Exception {
	private static final long serialVersionUID = -1337068861323891652L;

	public PrimaryKeyException(String message){
		super(message);
	}
}
