/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommonFilterImpl implements CommonFilter {
	protected ArrayList<PipedInputStream> in;
	protected ArrayList<PipedOutputStream> out;
	
	public CommonFilterImpl() {
		this.in = new ArrayList<PipedInputStream>(Arrays.asList(new PipedInputStream(), new PipedInputStream()));
		this.out = new ArrayList<PipedOutputStream>(Arrays.asList(new PipedOutputStream(), new PipedOutputStream()));
	}

	public void connectOutputTo(CommonFilter nextFilter, int portNum) throws IOException {
		out.get(portNum).connect(nextFilter.getPipedInputStream(portNum));
	}
	public void connectInputTo(CommonFilter previousFilter, int portNum) throws IOException {
		in.get(portNum).connect(previousFilter.getPipedOutputStream(portNum));
	}
	public PipedInputStream getPipedInputStream(int portNum) {
		return in.get(portNum);
	}
	public PipedOutputStream getPipedOutputStream(int portNum) {
		return out.get(portNum);
	}
	
	abstract public boolean specificComputationForFilter() throws IOException;
	// Implementation defined in Runnable interface for thread
	public void run() {
		try {
			specificComputationForFilter();
		} catch (IOException e) {
			if (e instanceof EOFException) return;
			else System.out.println(e);
		} finally {
			closePorts();
		}
	}
	private void closePorts() {
		try {
			out.get(0).close(); 
			in.get(0).close();
			out.get(1).close();
			in.get(1).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
