import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {
	private static String id = "unknown";
	private static Logger logger;
	public static void main(String[] args) throws NotBoundException, IOException, Exception {
		ServerIF server; // 인터페이스 생성 후 Lookup으로 찾기
		BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
		logger = new Logger("ClientLog.txt"); // 파일 생성
		try {
			server = (ServerIF)Naming.lookup("Server");
			checkLogin(server, objReader);
			while(true) {
				showMenu();
				System.out.print("Input : "); String userInput = objReader.readLine().trim();
				try {
					switch(userInput) {
					case "1" :
						printAllData(server.getAllStudentData());
						break;
					case "2" :
						printAllData(server.getAllCourseData());
						break;
					case "3" :
						addStudent(server, objReader);
						break;
					case "4" :
						deleteStudent(server, objReader);
						break;
					case "5" :
						addCourse(server, objReader);
						break;
					case "6" :
						deleteCourse(server, objReader);
						break;
					case "7" :
						makeRegistration(server, objReader);
						break;
					case "8" :
						printAllData(server.getAllRegistrationData());
						break;
					case "x" :
						return;
					default :
						System.out.println("*************** Please Input Valid Values ***************");
					}
				}catch (NullDataException e) {
					System.out.println(e.getMessage());
					continue;
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (TokenTimeOutException e) {
			System.out.println();
			System.out.println(e.getMessage());
			System.out.println();
			main(args);
		} catch (TokenDifferentException e) {
			System.out.println();
			System.out.println("*************** " + e.getMessage()+ " ***************");
			System.out.println();
			main(args);
		}
	} 
	private static void addStudent(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, TokenTimeOutException, TokenDifferentException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		System.out.println("-------- Student Information --------");
		System.out.print("StudentID :  "); String studentId = objReader.readLine().trim();
		System.out.print("StudentName :  "); String studentName = objReader.readLine().trim();
		System.out.print("student Department :  "); String studentDept = objReader.readLine().trim();
		System.out.print("student Completed Course List :  "); String completedCourses = objReader.readLine().trim();
		if(server.addStudent(studentId + " " + studentName + " " + studentDept + " " + completedCourses)) System.out.println("Success");
		else System.out.println("Already existed StudentId, Please Input Again");
	}
	
	private static void deleteStudent(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, TokenTimeOutException, TokenDifferentException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		System.out.print("Student ID :   ");
		if(server.deleteStudent(objReader.readLine().trim())) System.out.println("Success");
		else System.out.println("Fail");
	}
	
	private static void addCourse(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, TokenTimeOutException, TokenDifferentException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		System.out.println("-------- Course Information --------");
		System.out.print("CourseID :  "); String courseId = objReader.readLine().trim();
		System.out.print("ProfessorName :  "); String professorName = objReader.readLine().trim();
		System.out.print("CourseName :  "); String courseName = objReader.readLine().trim();
		System.out.print("Completed Course List :  "); String completedCourses = objReader.readLine().trim();
		
		if(server.addCourse((courseId + " " + professorName + " " + courseName + " " + completedCourses))) System.out.println("Success");
		else System.out.println("Already existed CourseId, Please Input Again");
	}
	
	private static void deleteCourse(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, TokenTimeOutException, TokenDifferentException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		System.out.print("Course ID :   ");
		if(server.deleteCourse(objReader.readLine().trim())) System.out.println("Success");
		else System.out.println("Fail");
	}
	
	private static void makeRegistration(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, TokenTimeOutException, TokenDifferentException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);

		System.out.print("Student ID :  "); String studentId = objReader.readLine().trim();
		System.out.print("Course ID List:  "); String courseIdList = objReader.readLine().trim();
		int num = server.makeRegistration(studentId + " " + courseIdList);
		switch(num) {
		case 0 :
			System.out.println("Success for registration");
			break;
		case 1 :
			System.out.println("Can't find studentId");
			break;
		case 2 :
			System.out.println("Can't find courseId");
			break;
		case 3 :
			System.out.println("Please check preCourse");
			break;
		case 4 :
			System.out.println("Already registration");
			break;
		default :
		}
			
	}
	
	private static void printAllData(ArrayList<?> dataList) {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		System.out.println();
		for(int i=0; i<dataList.size(); i++) {
			System.out.println(i+1 + ". " + dataList.get(i));
		}
	}
	
	private static void checkLogin(ServerIF server, BufferedReader objReader) throws IOException, RemoteException, Exception {
		id = "unknown";
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		while(true) {
			System.out.println("-------- Input your ID AND PASSWORD --------");
			System.out.println("Exit input 'x' in ID");
			System.out.print("Input ID : "); String inputId = objReader.readLine().trim();
		
			if(inputId.equals("x")) {
				System.exit(0);
			}
			
			System.out.print("Input PASSWORD : "); String inputPw = objReader.readLine().trim();
			Token returnValue = server.checkLogin(inputId + " " + inputPw);
			switch(returnValue.getReturnValue()) {
			case 0 :
				System.out.println("WelCome To Client");
				break;
			case 1 :
				System.out.println("Incorrect Id or Pw. Please Input Again");
				break;
			case 2 :
				System.out.println("Incorrect PassWord. Please Input Again");
				break;
			default :
				System.out.println("Incorrect Id&Pw. Please Input Again");
			}
			if(returnValue.getReturnValue() == 0) {
				id = inputId;
				break;
			}
		}
	}

	private static void showMenu() {
		System.out.println();
		System.out.println("*************** Menu ***************");
		System.out.println("1. List Students");
		System.out.println("2. List Courses");
		System.out.println("3. Add Student");
		System.out.println("4. Delete Student");
		System.out.println("5. Add Course");
		System.out.println("6. Delete Course");
		System.out.println("7. Make Reservation");
		System.out.println("8. List Reservation");
		System.out.println("x. Exit");
		System.out.println("*************** Menu ***************");
	}
}