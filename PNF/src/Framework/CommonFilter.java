/**
 * Copyright(c) 2019 All rights reserved by JU Consulting
 */
package Framework;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public interface CommonFilter extends Runnable{
    public void connectOutputTo(CommonFilter filter, int portNum) throws IOException; // Output���� ����
    public void connectInputTo(CommonFilter filter, int portNum) throws IOException; // Input���� ����
    public PipedInputStream getPipedInputStream(int portNum); // ������ �������� ���� �޴´�
    public PipedOutputStream getPipedOutputStream(int portNum); // �����°ſ��� �޴´�
}
