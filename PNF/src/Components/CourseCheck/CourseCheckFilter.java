package Components.CourseCheck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import Framework.CommonFilterImpl;

public class CourseCheckFilter extends CommonFilterImpl{
	ArrayList<String> tempCourseList;
	boolean isCourseTF;

	public CourseCheckFilter(boolean isCourseTF, String... courses) {
		this.tempCourseList = new ArrayList<String>();
		for(String course : courses) {
			this.tempCourseList.add(course);
		}
		this.isCourseTF = isCourseTF;
	}

	@Override
	public boolean specificComputationForFilter() throws IOException {
		int checkBlank = 4;
		int numOfBlank = 0;
		int checkOfdigit = 4;
		int digitOfCourse = 0;
		int idx = 0;
		int count = 0;
		int storeIdx = 0;
		ArrayList<String> completedCourse = new ArrayList<String>();
		byte[] buffer = new byte[80];
		byte[] bytesOfCourse = new byte[5];
		int byte_read = 0;
		int getIndex = 0;
		boolean check = false;

		while(true) {
			while(byte_read != '\n' && byte_read != -1) {
				byte_read = in.get(0).read();
				if(byte_read != -1) buffer[idx++] = (byte)byte_read;
				if(digitOfCourse > checkOfdigit) {
					completedCourse.add(new String(bytesOfCourse, StandardCharsets.UTF_8));
					digitOfCourse = 0;
				}
				if(byte_read == ' ') {
					numOfBlank++;
					continue;
				}
				if(numOfBlank == 3) storeIdx = idx;
				if(numOfBlank >= checkBlank) {bytesOfCourse[digitOfCourse++] = (byte)byte_read;}
			}
			
			for(String course : tempCourseList) { // 12345, 23456
				for(int i=0; i<completedCourse.size(); i++) {
					if(completedCourse.get(i).equals(course)) {
						check = true;
						getIndex = i;
					}
				}
				if(numOfBlank != 0 && !check && isCourseTF) { // courseId가 없을 때 추가
					String blankCourse = " " + course;
					byte[] arrtemp = blankCourse.getBytes();
					if(idx>2 && count == 0) {
						idx = idx-2;
						count++;
					}
					else if(idx>1 && count != 0) {idx = idx-1;}
					
					for(int i=0; i<arrtemp.length; i++) {buffer[idx++] = arrtemp[i];}
					
					buffer[idx++] = ((byte)('\n'));
				}
				if(numOfBlank !=0 && check && !isCourseTF) {
					completedCourse.remove(getIndex);
				}
				check = false;
			}
			
			if(!isCourseTF) {
				for(String s : completedCourse) {
					buffer[storeIdx++] = ' ';
					byte[] strToByte = s.getBytes();
					for(int i=0; i<strToByte.length; i++) {
						buffer[storeIdx++] = strToByte[i];
					}
				}
				buffer[storeIdx++] = ((byte)('\n'));
			}
			if(isCourseTF) for(int i=0; i<idx; i++) out.get(0).write((char)buffer[i]);
			if(!isCourseTF) for(int i = 0; i<storeIdx; i++) out.get(0).write((char)buffer[i]);
			
			if (byte_read == -1) return true;
			completedCourse.clear();
			digitOfCourse = 0;
			count = 0;
			idx = 0;
			numOfBlank = 0;
			byte_read = '\0';
		}
	}
}