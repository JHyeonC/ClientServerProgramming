/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L; //Default serializable value  
    private String message;
	private EventId eventId;
	private ArrayList<String> array;

	public Event(EventId id, String text ) {
		this.message = text;
		this.eventId = id;
	}
	public Event(EventId id, String text, ArrayList<String> array) {
		this.eventId = id;
		this.message = text;
		this.array = array;
	}
	public Event(EventId id ) {
		this.message = null;
		this.eventId = id;
	}
	public EventId getEventId() {
		return eventId;
	}
	public String getMessage() {
		return message;
	}
	public ArrayList<String> getArray(){
		return array;
	}
}