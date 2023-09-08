import java.io.Serializable;
import java.util.StringTokenizer;

public class Login implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String id;
	protected String salt;
	protected String pwSalt;
	
	public Login(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.id = stringTokenizer.nextToken();
		this.salt = stringTokenizer.nextToken();
		this.pwSalt = stringTokenizer.nextToken();
	}
	
	public boolean matchId(String inputId) {
		return this.id.equals(inputId);
	}
}
