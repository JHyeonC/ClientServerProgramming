import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Data extends UnicastRemoteObject implements DataIF{

	private static final long serialVersionUID = 1L;
	protected static StudentList studentList;
	protected static CourseList courseList;
	protected static RegistrationList registrationList;
	protected static LoginList loginList;
	protected static String id;
	private static Logger logger;
	
	protected Data() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//RMI를 통해 멀티프로세스가 가능하게 해야함
		//Server에 대한 객체를 만들어야함
		try {
			Data data = new Data();
			Naming.rebind("Data", data); // 브로커에 등록
			System.out.println("Data is Ready");

			studentList = new StudentList("Students.txt");
			courseList = new CourseList("Courses.txt");
			registrationList = new RegistrationList();
			loginList = new LoginList("login.txt");
			logger = new Logger("DataLog.txt"); // 파일생성
			
		} catch (RemoteException e) {
			System.out.println("Data isn't ready");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("Data isn't ready");
			e.printStackTrace();
		} catch (ConnectException e) {
			System.out.println("Data isn't connected");
			e.printStackTrace();
		}
	}

	
	public ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return studentList.getAllStudentRecords();
	}
	
	
	public ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return courseList.getAllCourseRecords();
	}

	
	public boolean addStudent(String studentInfo) throws RemoteException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(studentList.addStudentRecords(studentInfo)) return true;
		else return false;
	}
	
	
	public boolean deleteStudent(String studentId) throws RemoteException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(studentList.deleteStudentRecords(studentId)) return true;
		else return false;
	}
	
	public boolean addCourse(String courseInfo) throws RemoteException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(courseList.addCourseRecords(courseInfo)) return true;
		else return false;
	}
	
	public boolean deleteCourse(String courseId) throws RemoteException {
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(courseList.deleteCourseRecords(courseId)) return true;
		else return false;
	}
	
	public boolean checkStudentId(String sutdentId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(studentList.isRegisteredStudent(sutdentId)) return true;
		else return false;
	}
	
	public boolean checkCourseId(String courseId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(courseList.isRegisteredCourse(courseId)) return true;
		else return false;
	}
	
	public ArrayList<Registration> getAllRegistrationData() throws RemoteException, NullDataException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return registrationList.getAllRegistrationRecords();
	}
	
	public boolean addRegistration(String userInput) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(registrationList.addRegistrationRecords(userInput)) return true;
		else return false;
	}
	
	public ArrayList<String> getCompletedList(String studentId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return studentList.getCompletedList(studentId);
	}
	
	public ArrayList<String> getPreCourseList(String cousreId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return courseList.getPreCourseList(cousreId);
	}
	
	public boolean checkLoginId(String inputId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		if(loginList.checkLoginId(inputId)) {
			id = inputId;
			return true;
		}
		else return false;
	}
	
	public String getSalt(String inputId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return loginList.getSalt(inputId);
	}
	
	public String getEncryption(String inputId) throws RemoteException{
		StackTraceElement command = new Throwable().getStackTrace()[0];
		logger.setLogMessage(id, command);
		
		return loginList.getEncryption(inputId);
	}
}