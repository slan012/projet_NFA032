package app;

public class ErreurSaisieException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErreurSaisieException(){
        super();
    }

    public ErreurSaisieException (String message){
        super(message);
    }

    public ErreurSaisieException (Throwable cause){
        super(cause);
    }

    public ErreurSaisieException(String message, Throwable cause){
        super(message, cause);
    }
}
