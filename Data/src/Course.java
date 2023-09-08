import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String courseId;
	protected String pName;
	protected String department;
	protected ArrayList<String> completedCourseList;
	
	public Course(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.courseId = stringTokenizer.nextToken();
		this.pName = stringTokenizer.nextToken();
		this.department = stringTokenizer.nextToken();
		this.completedCourseList = new ArrayList<String>();
		while(stringTokenizer.hasMoreTokens()) {
			this.completedCourseList.add(stringTokenizer.nextToken());
		}
	}
	public String toString() {
		String stringReturn = this.courseId + " " + this.pName + " " + this.department;
        for (int i = 0; i < this.completedCourseList.size(); i++) {
            stringReturn = stringReturn + " " + this.completedCourseList.get(i).toString();
        }
        return stringReturn;
	}
	
	public ArrayList<String> getPreCourseList(){
		return this.completedCourseList;
	}
	
	public boolean matchCourseID(String courseId) {
		return this.courseId.equals(courseId);
	}
}