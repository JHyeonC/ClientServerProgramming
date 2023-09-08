package Components.CourseCheck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Framework.CommonFilterImpl;

public class isSatisfiedFilter extends CommonFilterImpl{
	ArrayList<String> studentList = new ArrayList<String>();
    ArrayList<ArrayList<String>> studentCourses = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> courseList = new ArrayList<ArrayList<String>>();
    ArrayList<String> studentCourseRow = new ArrayList<String>();
    ArrayList<String> courseRow = new ArrayList<String>();
	@Override
	public boolean specificComputationForFilter() throws IOException {
		int checkBlank = 4;
        int numOfBlank = 0;
        int checkOfdigit = 4;
        int digitOfCourse = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        byte[] bytesOfCourse = new byte[5];
        byte[] bytesOfId = new byte[8];
        int byte_read = 0;
        boolean readStudentFinish = false;
        boolean readCourseFinish = false;
        
        while(true) {
        	if(!readStudentFinish) {
        		String addList = "";
        		while(byte_read != '\n' && byte_read != -1) {
        			byte_read = in.get(0).read();
        			if(byte_read == ' ') {
        				numOfBlank++;
        				buffer[idx++] = ' ';
        				addList = new String(buffer, StandardCharsets.UTF_8);
        				continue;
        			}
        			if(byte_read != -1) buffer[idx++] = (byte)byte_read;
        			if(numOfBlank == 0) bytesOfId[digitOfCourse++] = (byte)byte_read;
        			if(numOfBlank ==0 && digitOfCourse == 8) { // 학번 추가
        				studentCourseRow.add(new String(bytesOfId, StandardCharsets.UTF_8));
        				digitOfCourse = 0;
        			}
        			if(numOfBlank != 0 && digitOfCourse > checkOfdigit) { // 학생이 들은 과목 추가
        				studentCourseRow.add(new String(bytesOfCourse, StandardCharsets.UTF_8));
        				digitOfCourse = 0;
        			}
        			if(numOfBlank >= checkBlank) { // 학생이 들은 과목 byte 읽기
        				bytesOfCourse[digitOfCourse++] = (byte)byte_read;
        			}
        		}
        		if(!studentCourseRow.isEmpty()) {
        			String studentInfo = addList.trim();
        			String takeCourse = studentCourseRow.get(studentCourseRow.size()-1);
        			if(!studentInfo.substring(studentInfo.length()-5).equals(takeCourse)) {
 	        		   addList = addList.trim() + " " + takeCourse;
 	        	   }
 	        	   studentCourses.add(studentCourseRow);
        		}
        		if(!addList.isBlank()) {
		         	  studentList.add(addList.trim()); // 공백 제거
		        }
        		if(byte_read == -1) readStudentFinish = true;
 	            studentCourseRow = new ArrayList<String>();
 	            numOfBlank = 0;
 	            digitOfCourse = 0;
 	            idx = 0;
 	            byte_read = '\0';
 	            buffer = new byte[64];
        	}
        	if(readStudentFinish && !readCourseFinish) {
        		checkBlank = 3;
        		while(byte_read != '\n' && byte_read != -1) {
          		   byte_read = in.get(1).read();
          		   if(digitOfCourse > checkOfdigit) {
          			   courseRow.add(new String(bytesOfCourse, StandardCharsets.UTF_8));
          			   digitOfCourse = 0;
          		   }
          		   if(byte_read == ' ') {
          			   numOfBlank++;
          			   continue;
          		   }
          		   if(numOfBlank == 0) bytesOfCourse[digitOfCourse++] = (byte)byte_read;
          		   if(numOfBlank >= checkBlank) bytesOfCourse[digitOfCourse++] = (byte) byte_read;
          	    }
        		if(!courseRow.isEmpty()) {
        			courseList.add(courseRow);
        			courseRow = new ArrayList<String>();
        		}
        		if(byte_read == -1) readCourseFinish = true;
          	    numOfBlank = 0;
          	    digitOfCourse = 0;
          	    byte_read = '\0';
        	}
        	
        	if(readStudentFinish && readCourseFinish) {
        		for(ArrayList<String> v : studentCourses) devide(v);
     	       	return false;
        	}
        }
	}
	
	private void devide(ArrayList<String> studentCourses) throws IOException {
		int completedCount = studentCourses.size()-1;
 		int takeCount = 0;
 		for(int i=1; i<studentCourses.size(); i++) {
 			for(int j=0; j<courseList.size(); j++) {
 				if(studentCourses.get(i).equals(courseList.get(j).get(0)))
 					if(compare(studentCourses, courseList.get(j))) takeCount++;
 			}
 		}
 		String sID = studentCourses.get(0);
 		byte[] strToBytes = null;
 		
 		for(String v : studentList) {
 			if(v.substring(0, 8).equals(sID)) {
 				String s = v + "\n";
 				strToBytes = s.getBytes();
 				if(completedCount == takeCount) {
 					out.get(0).write(strToBytes);
 				}else {
 					out.get(1).write(strToBytes);
 				}
 			}
 		}
	}
	
	private boolean compare(ArrayList<String> studentCourses, ArrayList<String> courseList) {
 		int resultCount = courseList.size()-1;
 		int takeCount = 0;
 		if(courseList.size() == 1) return true;
 		for(int i=1; i<courseList.size(); i++) {
 			for(int j=1; j<studentCourses.size(); j++)
 				if(courseList.get(i).equals(studentCourses.get(j))) takeCount++;
 		}
 		if(resultCount == takeCount) return true;
 		return false;
	}
}