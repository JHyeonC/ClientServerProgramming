import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements ServerIF{
	private static final long serialVersionUID = 1L;
	private static DataIF data;
	private static Token token;
	private static Salt salt;
	private static String takenTokenValue;
	private static String id;
	private static Logger logger;
	
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception{
		//RMI�� ���� ��Ƽ���μ����� �����ϰ� �ؾ���
		//Server�� ���� ��ü�� ��������
		try {
			Server server = new Server();
			Naming.rebind("Server", server);
			System.out.println("Server is Ready");

			data = (DataIF)Naming.lookup("Data");
			salt = new Salt();
			token = new Token();
			logger = new Logger("ServerLog.txt"); // ���� ����
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Server isn't ready");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException, TokenTimeOutException, TokenDifferentException{
		
		if(takenTokenValue.equals(token.getToken())) {
			if(!token.checkTime(token.getTime())) {
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
				
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
			
			return data.getAllStudentData();
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}
	
	public ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException, TokenTimeOutException, TokenDifferentException{
		if(takenTokenValue.equals(token.getToken())) {
			if(!token.checkTime(token.getTime())) {
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
				
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
		
			return data.getAllCourseData();
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}

	public boolean addStudent(String studentInfo) throws RemoteException, TokenTimeOutException, TokenDifferentException {
		if(takenTokenValue.equals(token.getToken())) {
			if(!token.checkTime(token.getTime())) {
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
				
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
		
			if(data.addStudent(studentInfo)) return true;
			return false;
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}

	public boolean deleteStudent(String studentId) throws RemoteException, TokenTimeOutException, TokenDifferentException {
		if(takenTokenValue.equals(token.getToken())) {
			if(!token.checkTime(token.getTime())) {
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
				
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
		
			if(data.deleteStudent(studentId)) return true;
			return false;
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}
	
	public boolean addCourse(String courseInfo) throws RemoteException, TokenTimeOutException, TokenDifferentException{
		if(takenTokenValue.equals(token.getToken())) {
			if(!token.checkTime(token.getTime())) {
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
				
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
		
			if(data.addCourse(courseInfo)) return true;
			return false;
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}
	
	public boolean deleteCourse(String CourseId) throws RemoteException, TokenTimeOutException, TokenDifferentException{
		if(takenTokenValue.equals(token.getToken())) {
			if(!token.checkTime(token.getTime())) {
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
				
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
		
			if(data.deleteCourse(CourseId)) return true;
			return false;
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}

	public boolean checkStudentId(String studentId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(data.checkStudentId(studentId)) return true;
		else return false;
	}

	public boolean checkCourseId(String courseId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(data.checkCourseId(courseId)) return true;
		else return false;
	}
	
	public ArrayList<String> getCompleteList(String userInput) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return data.getCompletedList(userInput);
	}
	
	public ArrayList<String> getPreCourseList(String userInput) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return data.getPreCourseList(userInput);
	}
	
	public boolean checkData(String userInput) throws RemoteException {
		String[] temp = userInput.split(" ");
		String studentId = temp[0];
		String courseId = temp[1];
		int cnt = 0;
		ArrayList<String> sCompeleteList = getCompleteList(studentId); // studentId�� �ش��ϴ� ������ ����� �����
		ArrayList<String> cPreCourseList = getPreCourseList(courseId); // courseId�� �ش��ϴ� ���������
		
		if(cPreCourseList.size() == 0) { // �������� �ʾҴٸ� ���������� �ִ��� Ȯ��, �ش���� ���������� ���ٸ� true
			return true;
		}
		else {
			for(int i=0; i<cPreCourseList.size(); i++) {
				for(int j=0; j<sCompeleteList.size(); j++) {
					if(cPreCourseList.get(i).equals(sCompeleteList.get(j))) { // �ش� ������ ���������� �ش� �л��� ����� ���� �ִ��� Ȯ��
						cnt++; 
					}
					else if(cnt == cPreCourseList.size()) return true; // �ش� ������ ���������� �ش� �л��� ����� ����� ��ġ�ϸ� true
					else continue;
				}
			}
		return false;
		}
	}
	
//	public boolean makeRegistration(String userInput) throws RemoteException {
//		String[] list = userInput.split(" ", 2);
//		String studentId = list[0];
//		String courseIdList = list[1];
//		String[] courseList = courseIdList.split(" "); 
//		
//		if(checkStudentId(studentId)) { // studentId check
//			for(int i=0; i<courseList.length; i++) {
//				String temp = studentId + " " + courseList[i];
//				if(checkCourseId(courseList[i]) == true && checkData(temp) == true) { //courseId check & �������� check
//					if(i == courseList.length-1) {
//						if(addRegistration(userInput)) return true;
//						else return false;
//					}
//					continue;
//				}else {
//					break;
//				}
//			}
//		}
//		return false;
//	}
	
	public int makeRegistration(String userInput) throws RemoteException, TokenTimeOutException, TokenDifferentException {
		if(takenTokenValue.equals(token.getToken())) { // ������ ��ū�� ���� ��ȿ�� ��ū�� ������ Ȯ��
			if(!token.checkTime(token.getTime())) { // ��ū�� ���� �� �ð��� �޼ҵ尡 �Ҹ� �ð��� �� ��, ��ū�� ��ȿ���� üũ
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
		
			String[] list = userInput.split(" ", 2);
			String studentId = list[0];
			String courseIdList = list[1];
			String[] courseList = courseIdList.split(" ");
			
			if(checkStudentId(studentId)) { // studentId check
				for(int i=0; i<courseList.length; i++) {
					String takenOneString = studentId + " " + courseList[i]; // iD + courseList���� ������ ID + courseList[i]
					if(checkCourseId(courseList[i])) { // courseId check
						if(checkData(takenOneString)) { // �������� check
							if(i == courseList.length-1) { // �Էµ� studentId�� ��� courseId�� ���õ� ���ǿ� �ش��Ѵٸ�
								if(addRegistration(userInput)) return 0; // ������û ����
								else return 4; // check preCourse 
							}continue;
						}else return 3; // check preCourse
					}else return 2; // check courseId
				}
			}else return 1; //check studentId
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}

	public ArrayList<Registration> getAllRegistrationData() throws RemoteException, NullDataException, TokenTimeOutException, TokenDifferentException {
		if(takenTokenValue.equals(token.getToken())) {
			if(!token.checkTime(token.getTime())) {
				System.out.println("exception");
				throw new TokenTimeOutException("*************** Token is disallow ***************");
			}
				
			StackTraceElement command = new Throwable().getStackTrace()[0];
			logger.setLogMessage(id, command);
		
			return data.getAllRegistrationData();
		}
		throw new TokenDifferentException("�߱޵� ��ū�� ����� ��ū�� �ٸ��ϴ�.");
	}

	public boolean addRegistration(String userInput) throws RemoteException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(data.addRegistration(userInput)) return true;
		else return false;
	}
	
	// �α����� �Ҷ� �α��� ID, PW ���� Server�� �ѱ� ��, Data���� ���� üũ �� �� Ȯ���Ѵ�.
	// �� �� Sever���� Token�� Client�� ������, �α��� �� Server���� �Ѱ��� Token�� ������
	// Token�� Server���� �Ѱ��� Token�� �� �� �޼ҵ� ����
	
	public boolean checkLoginId(String inputId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(data.checkLoginId(inputId)) return true;
		return false;
	}
	
	public String getEncryption(String inputId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return data.getEncryption(inputId);
	}
	
	public String getSalt(String inputId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return data.getSalt(inputId);
	}
	
	public Token checkLogin(String userInput) throws RemoteException, IOException, NoSuchAlgorithmException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		String[] list = userInput.split(" ");
		String inputId = list[0];
		String inputPw = list[1];
		String getSalt = getSalt(inputId);
		String checkResult = salt.checkPassword(inputPw, getSalt);
		String encryption = getEncryption(inputId);
		
		if(checkLoginId(inputId) && checkResult.equals(encryption)) { // ID && PW ���ÿ� �¾����� return 0
			takenTokenValue = salt.makeToken(checkResult); // ��ū �� ����
			token.setToken(takenTokenValue); // ��ū �� ����
			token.setReturnValue(0);
			token.setTime(System.currentTimeMillis()); // ��ū ���� �ð� ����
			id = inputId;
			return token;
		}
		
		if(checkLoginId(inputId)) { // ��й�ȣ Ʋ������ return 2
			token.setToken(null);
			token.setReturnValue(2);
			return token;
		}
		
		token.setToken(null);
		token.setReturnValue(1); // ID�� Ʋ���� �� return 1
		return token;
	}
}