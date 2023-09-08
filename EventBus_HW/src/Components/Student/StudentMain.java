/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class StudentMain {
	private static String studentId;
	public StudentMain() {
		studentId = "";
	}
	public static void main(String args[]) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("** StudentMain(ID:" + componentId + ") is successfully registered. \n");

		StudentComponent studentsList = new StudentComponent("Students.txt");
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			int eventQueueSize = eventQueue.getSize();
			for (int i = 0; i < eventQueueSize; i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case ListStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeStudentList(studentsList)));
					break;
				case RegisterStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerStudent(studentsList, event.getMessage())));
					break;
				case DeleteStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage())));
					break;
				case ListStudentsRegistered:
					printLogEvent("Get", event); //CLient input에서 받아온 값
					setStudentId(event.getMessage());
					if(checkStudent(studentsList) == true) {
						eventBus.sendEvent(new Event(EventId.ListCourseRegistered, event.getMessage(), 
								getStudentArray(studentsList))); // 학생이 들었던 과목을 보냄
					}else eventBus.sendEvent(new Event(EventId.ClientOutput, "Student info is not correct" + "\n"));
					break;
				case QuitTheSystem:
					printLogEvent("Get", event);
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}
	private static void setStudentId(String message) {
		String[] temp = message.split(" ");
		studentId = temp[0];
		System.out.println("set inputStudentId = " + studentId);
	}
	private static boolean checkStudent(StudentComponent studentsList) {
		if(studentsList.isRegisteredStudent(studentId)) return true;
		return false;
	}
	private static ArrayList<String> getStudentArray(StudentComponent studentsList){
		return studentsList.getStudentArray(studentId);
	}
	private static String deleteStudent(StudentComponent studentsList, String message) {
		if(studentsList.deleteStudent(message)) return "This student is successfully delete." + "\n";
		return "This student is not Existed" + "\n";
	}
	private static String registerStudent(StudentComponent studentsList, String message) {
		Student student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.add(student);
			return "This student is successfully added." + "\n";
		} else
			return "This student is already registered." + "\n";
	}
	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = "";
		for (int j = 0; j < studentsList.vStudent.size(); j++) {
			returnString += studentsList.getStudentList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
