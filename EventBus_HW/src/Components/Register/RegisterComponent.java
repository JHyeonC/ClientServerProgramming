package Components.Register;

import java.util.ArrayList;

public class RegisterComponent {
	protected ArrayList<Register> vRegister;
	
	public RegisterComponent() {
		vRegister = new ArrayList<Register>();
	}
	
	public ArrayList<Register> getRegisterList(){
		return vRegister;
	}
	
	public boolean addList(String message) {
		String[] temp = message.split(" ");
		String studentId = temp[0];
		String courseId = temp[1];
		System.out.println("courseId = " + courseId);
		
		for(int i=0; i<vRegister.size(); i++) {
			Register register = (Register) this.vRegister.get(i);
			if(register.match(studentId)) { // 등록된 studentId
				for(int j=0; j<register.registerCourseList.size(); j++) { // course 확인
					if(register.registerCourseList.get(j).equals(courseId)) return false; // 이미 등록된 course 라면 false;
				}
				if(register.registerCourseList.add(courseId)) return true;
			}
		}
		if(vRegister.add(new Register(message))) return true;
		return false;
	}
}