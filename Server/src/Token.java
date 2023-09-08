import java.io.Serializable;
public class Token implements Serializable{
	private static final long serialVersionUID = 1L;
	private int returnValue;
	private String token;
	private Long time;
	public Token() {}
	public Token(int returnValue, String token, Long time) {
		this.returnValue = returnValue;
		this.token = token;
		this.time = time;
	}
	public boolean checkTime(Long makeTokenTime) {
		Long currentTime = System.currentTimeMillis();
		if(currentTime - makeTokenTime < 9000000) return true;
		else return false;
	}
	
	// Getter & Setter , Setter 보안성 문제..
	public void setToken(String token) {
		this.token = token;
	}
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}
	public void setTime(Long time) { 
		this.time = time;
	}
	public String getToken() {
		return this.token;
	}
	public int getReturnValue() {
		return this.returnValue;
	}
	public Long getTime() {
		return this.time;
	}
}
