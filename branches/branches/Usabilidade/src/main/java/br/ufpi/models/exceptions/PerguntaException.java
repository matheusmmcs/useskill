package br.ufpi.models.exceptions;

public class PerguntaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerguntaException() {
		super("Pergunta não encontrada");
	}

	public PerguntaException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
		//super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PerguntaException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PerguntaException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PerguntaException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
