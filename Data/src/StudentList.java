import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StudentList {
	protected ArrayList<Student> vStudent;
	protected ArrayList<String> temp;
	
	public StudentList(String sStudentFileName) throws FileNotFoundException, IOException {
		BufferedReader objStudentFile = new BufferedReader(new FileReader(sStudentFileName));
		this.vStudent = new ArrayList<Student>();
		while (objStudentFile.ready()) {
			String stuInfo = objStudentFile.readLine();
			if (!stuInfo.equals("")) {
				this.vStudent.add(new Student(stuInfo));
			}
		}
		objStudentFile.close();
	}

	public ArrayList<Student> getAllStudentRecords() throws NullDataException {
		if(this.vStudent.size() == 0) throw new NullDataException("************* Student Data is null *************");
		return this.vStudent;
	}
	
	public boolean addStudentRecords(String studentInfo){
		String[] getStudentId = studentInfo.split(" ");
		String StudentId = getStudentId[0];
		
		for(int i=0; i<this.vStudent.size(); i++) {
			Student student = (Student) this.vStudent.get(i);
			if(student.match(StudentId)) return false; // �̹� ��ϵ� studentId�� �ִ��� Ȯ��
		}
		
		if(this.vStudent.add(new Student(studentInfo))) return true; // ���ٸ� ���
		else return false;
	}
	
	public boolean deleteStudentRecords(String studentId) {
		for (int i = 0; i < this.vStudent.size(); i++) {
			Student student = (Student) this.vStudent.get(i);
			if (student.match(studentId)) {
				if(this.vStudent.remove(student)) return true; 
				else return false;
			}
		}
		return false;
	}

	public boolean isRegisteredStudent(String studentId) {
		for (int i = 0; i < this.vStudent.size(); i++) {
			Student student = (Student) this.vStudent.get(i);
			if(student.match(studentId)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getCompletedList(String studentId) {
		this.temp = new ArrayList<String>();
		for(int i=0; i<this.vStudent.size(); i++) {
			Student student = (Student) this.vStudent.get(i);
			if(student.match(studentId)) {
				temp = student.completedCoursesList; // studentId�� �ش��ϴ� ������ ����� ������ temp�� ����
			}
		}
		return temp;
	}
	
	
}