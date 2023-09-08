import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoginList {
	protected ArrayList<Login> vLogin;
	
	public LoginList(String sLoginFileName) throws FileNotFoundException, IOException{
		BufferedReader objLoginFile = new BufferedReader(new FileReader(sLoginFileName));
		this.vLogin = new ArrayList<Login>();
		while(objLoginFile.ready()) {
			String logInfo = objLoginFile.readLine();
			if(!logInfo.equals("")) {
				this.vLogin.add(new Login(logInfo));
			}
		}
		objLoginFile.close();
	}
	
	public boolean checkLoginId(String InputId) {
		for(int i=0; i<vLogin.size(); i++) {
			Login login = (Login) this.vLogin.get(i);
			if(login.matchId(InputId)) return true;
		}
		return false;
	}
	
	public String getSalt(String inputId) {
		for(int i=0; i<vLogin.size(); i++) {
			Login login = (Login) this.vLogin.get(i);
			if(login.matchId(inputId)) return vLogin.get(i).salt;
		}
		return null;
	}
	
	public String getEncryption(String InputId) {
		for(int i=0; i<vLogin.size(); i++) {
			Login login = (Login) this.vLogin.get(i);
			if(login.matchId(InputId)) return vLogin.get(i).pwSalt;
		}
		return null;
	}
}
