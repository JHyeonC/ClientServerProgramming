/**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */
package Framework;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public interface CommonFilter extends Runnable{
    public void connectOutputTo(CommonFilter filter, int portNum) throws IOException; // Output으로 연결
    public void connectInputTo(CommonFilter filter, int portNum) throws IOException; // Input으로 연결
    public PipedInputStream getPipedInputStream(int portNum); // 들어오는 파이프의 값을 받는다
    public PipedOutputStream getPipedOutputStream(int portNum); // 나가는거에서 받는다
}
