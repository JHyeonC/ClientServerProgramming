package Components.Register;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Register {
	protected String studentId;
	protected ArrayList<String> registerCourseList;
	
	public Register(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.studentId = stringTokenizer.nextToken();
		this.registerCourseList = new ArrayList<String>();
		while(stringTokenizer.hasMoreTokens()) {
			this.registerCourseList.add(stringTokenizer.nextToken());
		}
	}
	
	public boolean match(String studentId) {
		return this.studentId.equals(studentId);
	}
	
	public String getString() {
		String stringReturn = this.studentId;
		for(int i=0; i<registerCourseList.size(); i++) {
			stringReturn = stringReturn + " " + registerCourseList.get(i).toString();
		}
		return stringReturn;
	}
}
