/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class CourseMain {
	private static String courseId;
	public CourseMain() {
		courseId = "";
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("CourseMain (ID:" + componentId + ") is successfully registered...");

		CourseComponent coursesList = new CourseComponent("Courses.txt");
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
				case ListCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeCourseList(coursesList)));
					break;
				case RegisterCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerCourse(coursesList, event.getMessage())));
					break;
				case DeleteCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteCourse(coursesList, event.getMessage())));
					break;
				case ListCourseRegistered:
					printLogEvent("Get", event);
					setId(event.getMessage());
					if(checkCourse(coursesList) == true && checkPreCourse(coursesList, event.getArray()) == true) {
						eventBus.sendEvent(new Event(EventId.TryReservation, event.getMessage()));
					}
					else eventBus.sendEvent(new Event(EventId.ClientOutput, "Course Info is not correct" + "\n"));
					break;
				case QuitTheSystem:
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}
	private static boolean checkPreCourse(CourseComponent CoursesList, ArrayList<String> arr) {
		int count = 0;
		// cArray 과목을 듣기 위한 선수과목
		// sArray 학생이 들었던 과목
		ArrayList<String> cArray = CoursesList.getCourseArray(courseId);
		ArrayList<String> sArray = arr;
		if(cArray.size()==0) return true;
		else {
			for(int i=0; i<cArray.size(); i++) {
				for(int j=0; j<sArray.size(); j++) {
					if(cArray.get(i).equals(sArray.get(j))) count++;
					else if(count == cArray.size()) return true;
					else continue;
				}
			}
			return false;
		}
	}
	private static void setId(String message) {
		String[] temp = message.split(" ");
		courseId = temp[1];
		System.out.println("set inputCourseId = " + courseId);
	}
	
	private static boolean checkCourse(CourseComponent CoursesList) {
		if(CoursesList.isRegisteredCourse(courseId)) return true;
		return false;
	}
	
	private static String deleteCourse(CourseComponent coursesList, String message) {
		if(coursesList.deleteCourse(message)) return "This course is successfully delete." + "\n";
		return "This course is not Existed" + "\n";
	}
	private static String registerCourse(CourseComponent coursesList, String message) {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			return "This course is successfully added." + "\n";
		} else return "This course is already registered." + "\n";
	}
	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = "";
		for (int j = 0; j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage() +
				" ArrayList: " + event.getArray());
	}
}
