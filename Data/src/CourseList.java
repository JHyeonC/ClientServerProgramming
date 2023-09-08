import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CourseList {
	protected ArrayList<Course> vCourse;
	protected ArrayList<String> getList;
	
	public CourseList(String sCourseFileName) throws FileNotFoundException, IOException{
		BufferedReader objCousrseFile = new BufferedReader(new FileReader(sCourseFileName));
		this.vCourse = new ArrayList<Course>();
		while(objCousrseFile.ready()) {
			String courInfo = objCousrseFile.readLine();
			if(!courInfo.equals("")) {
				this.vCourse.add(new Course(courInfo));
			}
		}
		objCousrseFile.close();
	}
	
	public ArrayList<Course> getAllCourseRecords() throws NullDataException{
		if(this.vCourse.size() == 0) throw new NullDataException("************* Course Data is null *************");
		return this.vCourse;
	}
	
	public boolean addCourseRecords(String courseInfo){
		String[] getCourseId = courseInfo.split(" ");
		String courseId = getCourseId[0];
		for(int i=0; i< this.vCourse.size(); i++) {
			Course course = (Course) this.vCourse.get(i);
			if(course.matchCourseID(courseId)) return false; // �̹� ��ϵ� CourseId�� �ִ��� Ȯ��
		}
		if(this.vCourse.add(new Course(courseInfo))) return true; // ���ٸ� ���
		else return false;
	}
	
	public boolean deleteCourseRecords(String courseId) {
		for (int i = 0; i < this.vCourse.size(); i++) {
			Course course = (Course) this.vCourse.get(i);
			if (course.matchCourseID(courseId)) {
				if(this.vCourse.remove(course)) return true;
				else return false;
			}
		}
		return false;
	}
	
	public boolean isRegisteredCourse(String courseId) {
		for(int i=0; i<this.vCourse.size(); i++) {
			Course course = (Course) this.vCourse.get(i);
			if(course.matchCourseID(courseId)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getPreCourseList(String courseId){
		this.getList = new ArrayList<String>();
		for(int i=0; i<this.vCourse.size(); i++) {
			Course course = (Course) this.vCourse.get(i);
			if(course.courseId.equals(courseId)) {
				getList = course.completedCourseList; // courseId�� �ش��ϴ� ���������� ������ getList�� ����
			}
		}
		return getList;
	}
}