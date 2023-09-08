import java.util.ArrayList;

public class RegistrationList {
	protected ArrayList<Registration> vRegistration;
	
	public RegistrationList() {
		this.vRegistration = new ArrayList<Registration>();
	}
	
	public boolean addRegistrationRecords(String userInput) {
		ArrayList<String> arr = new ArrayList<String>();
		String[] temp = userInput.split(" ");
		for(int i=0; i<temp.length; i++) {
			arr.add(temp[i]);
		}
		String studentId = arr.get(0); // arr.get(0) == studentId, arr.get(나머지) courseId
		
		for(int i=0; i<this.vRegistration.size(); i++) { // 수강신청 중복 확인
			Registration registration = (Registration) this.vRegistration.get(i);
			if(registration.match(studentId)) {
				for(int j=0; j<registration.CourseIdList.size(); j++) {
					for(int k=1; k<arr.size(); k++) {
						if(registration.CourseIdList.get(j).equals(arr.get(k))) return false;
					}
				}
			}
		}
	
		if(this.vRegistration.size() == 0) { // vRegistration에 아무것도 없다면 처음으로 값을 추가
			if(this.vRegistration.add(new Registration(userInput))) return true;
		}
		
		for(int i=0; i<this.vRegistration.size(); i++) { // vRegistration에 해당하는 Id 값이 있다면 찾아서 course 추가
			Registration registration = (Registration) this.vRegistration.get(i);
			if(registration.match(studentId)) {
				for(int j=1; j<arr.size(); j++) {
					registration.CourseIdList.add(arr.get(j));
				}
				return true;
			}
			continue;
		}
		
		if(this.vRegistration.add(new Registration(userInput))) return true;
		return false;
	}
	
	public ArrayList<Registration> getAllRegistrationRecords() throws NullDataException{
		if(this.vRegistration.size() == 0) throw new NullDataException("************* Registration Data is null *************");
		return this.vRegistration;
	}
}