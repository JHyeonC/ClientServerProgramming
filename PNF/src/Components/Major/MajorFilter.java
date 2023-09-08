package Components.Major;

import java.io.IOException;

import Framework.CommonFilterImpl;

public class MajorFilter extends CommonFilterImpl{
	private String major;
	private int portNum;
	private boolean isMajorTF;
	
	public MajorFilter(String major, int portNum, boolean isMajorTF) {
		this.major = major;
		this.portNum = portNum;
		this.isMajorTF = isMajorTF;
	}

	@Override
	public boolean specificComputationForFilter() throws IOException {
		int checkBlank = 3; 
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean MajorCheck = false;    
        int byte_read = 0;
        int tmp = 0;

        while(true) {          
            while(byte_read != '\n' && byte_read != -1) {
            	byte_read = in.get(portNum).read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if(numOfBlank == checkBlank) {
                	tmp = idx;
                }
            }
            if(isMajorTF && buffer[tmp-2] == major.charAt(0) && buffer[tmp-1] == major.charAt(1)) MajorCheck = true;
            else if(!isMajorTF && buffer[tmp-2] != major.charAt(0) && buffer[tmp-1] != major.charAt(1)) MajorCheck = true;
            
            if(MajorCheck == true) {
                for(int i=0; i<idx; i++) {
                	out.get(portNum).write((char)buffer[i]); // 찾아서 넘기는 곳
                }
            }
            MajorCheck = false;
            if (byte_read == -1) return true; // while문 끝내는방법
            idx = 0;
            numOfBlank = 0;
            tmp = 2;
            byte_read = '\0';
        }
	}
}