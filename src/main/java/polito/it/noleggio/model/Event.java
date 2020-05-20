package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	//TEMPO E TIPO DI EVENTO SONO SEMPRE PRESENTI
	
	//classe che ha solo un elenco di costanti che possono essere definite
	public enum EventType {
		NEW_CLIENT, CAR_RETURNED
	}
	
	//attributi che avremo sempre
	private LocalTime time;  //-> unico usato per fare i confronti
	private EventType type;
	
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}
	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	
	
	
	
}
