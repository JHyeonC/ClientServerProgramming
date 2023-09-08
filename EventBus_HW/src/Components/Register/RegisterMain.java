package Components.Register;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class RegisterMain {
	
	public static void main(String[] args) throws IOException, NotBoundException{
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("RegisterMain (ID:" + componentId + ") is sucessfully registered...");
		
		RegisterComponent registerList = new RegisterComponent();
		Event event = null;
		boolean done = false;
		while(!done) {
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			int eventQueueSize = eventQueue.getSize();
			for(int i=0; i<eventQueueSize; i++) {
				event = eventQueue.getEvent();
				switch(event.getEventId()) {
				case TryReservation:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, register(registerList, event.getMessage())));
					break;
				case ListRegisters:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeRegisterList(registerList)));
					break;
				default:
					break;
				}
			}
		}
	}
	private static String register(RegisterComponent registerList, String message) {
		if(registerList.addList(message)) return "This register is success" + "\n";
		return "Already registration Try Again" + "\n";
	}
	private static String makeRegisterList(RegisterComponent registerList) {
		String returnString = "";
		for (int j = 0; j < registerList.vRegister.size(); j++) {
			returnString += registerList.getRegisterList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
