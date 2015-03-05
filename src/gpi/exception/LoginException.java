package gpi.exception;

public class LoginException extends Exception{
	private static final long serialVersionUID = -1337068861323891652L;

	public LoginException(String message){
		super(message);
	}
}
