public class TokenTimeOutException extends Exception{
	private static final long serialVersionUID = 1L;
	public TokenTimeOutException(String errorMessage) {
		super(errorMessage);
	}
}
