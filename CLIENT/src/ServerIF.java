import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

// Client ServerIF
public interface ServerIF extends Remote{ // stub
	ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException, TokenTimeOutException, TokenDifferentException;
	ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException, TokenTimeOutException, TokenDifferentException;
	boolean addStudent(String studentInfo) throws RemoteException, TokenTimeOutException, TokenDifferentException;
	boolean deleteStudent(String studentId) throws RemoteException, TokenTimeOutException, TokenDifferentException;
	boolean addCourse(String courseInfo) throws RemoteException, TokenTimeOutException, TokenDifferentException;
	boolean deleteCourse(String courseId) throws RemoteException, TokenTimeOutException, TokenDifferentException;
	int makeRegistration(String userInput) throws RemoteException, TokenTimeOutException, TokenDifferentException;
	ArrayList<Registration> getAllRegistrationData() throws RemoteException, NullDataException, TokenTimeOutException, TokenDifferentException;
	Token checkLogin(String userInput) throws RemoteException, IOException, NoSuchAlgorithmException, TokenTimeOutException, TokenDifferentException;
}