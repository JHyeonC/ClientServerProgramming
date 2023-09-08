package Components.Major;

import java.io.IOException;

import Framework.CommonFilterImpl;

public class StudentIdFilter extends CommonFilterImpl{
	private String studentId;
	private int portNum;
	
	public StudentIdFilter(String studentId, int portNum) {
		this.studentId = studentId;
		this.portNum = portNum;
	}

	@Override
	public boolean specificComputationForFilter() throws IOException {
		int checkBlank = 4; 
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean studentIdCheck = false;    
        int byte_read = 0;
        
        while(true) {          
        	// check "CS" on byte_read from student information
            while(byte_read != '\n' && byte_read != -1) {
            	byte_read = in.get(portNum).read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if(numOfBlank == 0 && buffer[0] == studentId.charAt(0) && buffer[1] == studentId.charAt(1) 
                		&& buffer[2] == studentId.charAt(2) && buffer[3] == studentId.charAt(3)) studentIdCheck = true;
            }      
            if(studentIdCheck == true) {
                for(int i = 0; i<idx; i++) {
                    out.get(portNum).write((char)buffer[i]);
                }
                studentIdCheck = false;
            }
            if (byte_read == -1) return true;
            buffer[3] = '-';
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
        
	}

}
