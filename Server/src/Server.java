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
		//RMI를 통해 멀티프로세스가 가능하게 해야함
		//Server에 대한 객체를 만들어야함
		try {
			Server server = new Server();
			Naming.rebind("Server", server);
			System.out.println("Server is Ready");

			data = (DataIF)Naming.lookup("Data");
			salt = new Salt();
			token = new Token();
			logger = new Logger("ServerLog.txt"); // 파일 생성
			
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
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
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
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
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
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
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
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
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
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
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
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
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
		ArrayList<String> sCompeleteList = getCompleteList(studentId); // studentId에 해당하는 이전에 들었던 과목들
		ArrayList<String> cPreCourseList = getPreCourseList(courseId); // courseId에 해당하는 선수과목들
		
		if(cPreCourseList.size() == 0) { // 수강하지 않았다면 선수과목이 있는지 확인, 해당과목에 선수과목이 없다면 true
			return true;
		}
		else {
			for(int i=0; i<cPreCourseList.size(); i++) {
				for(int j=0; j<sCompeleteList.size(); j++) {
					if(cPreCourseList.get(i).equals(sCompeleteList.get(j))) { // 해당 과목의 선수과목이 해당 학생이 들었던 과목에 있는지 확인
						cnt++; 
					}
					else if(cnt == cPreCourseList.size()) return true; // 해당 과목의 선수과목이 해당 학생이 들었던 과목과 일치하면 true
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
//				if(checkCourseId(courseList[i]) == true && checkData(temp) == true) { //courseId check & 선수과목 check
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
		if(takenTokenValue.equals(token.getToken())) { // 발행한 토큰과 현재 유효한 토큰이 같은지 확인
			if(!token.checkTime(token.getTime())) { // 토큰이 발행 된 시간과 메소드가 불린 시간을 비교 후, 토큰이 유효한지 체크
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
					String takenOneString = studentId + " " + courseList[i]; // iD + courseList에서 한줄의 ID + courseList[i]
					if(checkCourseId(courseList[i])) { // courseId check
						if(checkData(takenOneString)) { // 선수과목 check
							if(i == courseList.length-1) { // 입력된 studentId와 모든 courseId가 제시된 조건에 해당한다면
								if(addRegistration(userInput)) return 0; // 수강신청 성공
								else return 4; // check preCourse 
							}continue;
						}else return 3; // check preCourse
					}else return 2; // check courseId
				}
			}else return 1; //check studentId
		}
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
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
		throw new TokenDifferentException("발급된 토큰과 저장된 토큰이 다릅니다.");
	}

	public boolean addRegistration(String userInput) throws RemoteException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(data.addRegistration(userInput)) return true;
		else return false;
	}
	
	// 로그인을 할때 로그인 ID, PW 값을 Server로 넘긴 후, Data에서 값을 체크 한 후 확인한다.
	// 그 후 Sever에서 Token을 Client로 보내줌, 로그인 후 Server에서 넘겨준 Token을 가지고
	// Token이 Server에서 넘겨준 Token과 비교 후 메소드 실행
	
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
		
		if(checkLoginId(inputId) && checkResult.equals(encryption)) { // ID && PW 동시에 맞았을때 return 0
			takenTokenValue = salt.makeToken(checkResult); // 토큰 값 발행
			token.setToken(takenTokenValue); // 토큰 값 설정
			token.setReturnValue(0);
			token.setTime(System.currentTimeMillis()); // 토큰 발행 시간 설정
			id = inputId;
			return token;
		}
		
		if(checkLoginId(inputId)) { // 비밀번호 틀렸을때 return 2
			token.setToken(null);
			token.setReturnValue(2);
			return token;
		}
		
		token.setToken(null);
		token.setReturnValue(1); // ID가 틀렸을 때 return 1
		return token;
	}
}