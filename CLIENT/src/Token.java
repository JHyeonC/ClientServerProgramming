import java.io.Serializable;

public class Token implements Serializable{

	private static final long serialVersionUID = 1L;
	private int returnValue;
	private String token;
	
	public Token() {
		
	}
	
	public Token(int returnValue, String token) {
		this.returnValue = returnValue;
		this.token = token;
	} 
	
	// Getter & Setter
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}

	public String getToken() {
		return this.token;
	}
	
	public int getReturnValue() {
		return this.returnValue;
	}
}
