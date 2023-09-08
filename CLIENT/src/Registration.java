import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Registration implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String studentId;
	protected ArrayList<String> CourseIdList;
	
	public Registration(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.studentId = stringTokenizer.nextToken();
		this.CourseIdList = new ArrayList<String>();
		while(stringTokenizer.hasMoreTokens()) {
			this.CourseIdList.add(stringTokenizer.nextToken());
		}
	}
	
	public boolean match(String studentId) {
		return this.studentId.equals(studentId);
	}
	
	public String toString() {
		String stringReturn = this.studentId;
        for (int i = 0; i < this.CourseIdList.size(); i++) {
            stringReturn = stringReturn + " " + this.CourseIdList.get(i).toString();
        }
        return stringReturn;
	}
}
