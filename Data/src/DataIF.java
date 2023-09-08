import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//Data DataIF
public interface DataIF extends Remote{  //interface를 만들어야 RMI호출 가능
	ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException;
	ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException;
	boolean addStudent(String studentInfo) throws RemoteException;
	boolean deleteStudent(String studentId) throws RemoteException;
	boolean addCourse(String courseInfo) throws RemoteException;
	boolean deleteCourse(String courseId) throws RemoteException;
	boolean checkStudentId(String studentId) throws RemoteException;
	boolean checkCourseId(String courseId) throws RemoteException;
	ArrayList<Registration> getAllRegistrationData() throws RemoteException, NullDataException;
	boolean addRegistration(String userInput) throws RemoteException;
	ArrayList<String> getPreCourseList(String cousreId) throws RemoteException;
	ArrayList<String> getCompletedList(String cousreId) throws RemoteException;
	boolean checkLoginId(String inputId) throws RemoteException;
	String getSalt(String inputId) throws RemoteException;
	String getEncryption(String inputId) throws RemoteException;
}