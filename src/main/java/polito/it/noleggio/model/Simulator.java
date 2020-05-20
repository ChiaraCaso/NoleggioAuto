package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue = new PriorityQueue<Event>();
	
	//PARAMETRI DI SIMULAZIONE 
	private int NC = 10; //numero di macchine
	private Duration T_IN = Duration.of(10, ChronoUnit.MINUTES); //intervallo tra i clienti
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(17, 00);
	
	//MODELLO DEL MONDO 
	private int nAuto ;
	
	//VALORI DA CALCOLARE
	private int clienti;
	private int insoddisfatti;
	
	//METODI PER IMPOSTARE I PARAMETRI 
	public void setNumCars (int N) {
		this.NC = N;
	}
	
	public void setClientFrequency (Duration d) { 
		this.T_IN = d;
	}

	//METODI PER RESTITUIRE I RISULTATI
	public int getClienti() {
		return clienti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	//SIMULAZIONE VERA E PROPRIA
	public void run() {
		
		//preparazione iniziale -> preparare sia le variabili del mondo che la coda degli eventi 
		this.nAuto = this.NC;
		this.clienti = this.insoddisfatti = 0;
		
		this.queue.clear();
		LocalTime oraArrivoCliente = this.oraApertura; //supponiamo che il 1 cliente arriva quando apre
		do {
			Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT);
			this.queue.add(e);
			
			oraArrivoCliente = oraArrivoCliente.plus(this.T_IN);
			
		} while (oraArrivoCliente.isBefore(this.oraChiusura));
		
		//esecuzione del ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
//			System.out.println(e);
			ProcessEvent(e);
			}
		}
	
	
	private void ProcessEvent (Event e) {
		
		switch (e.getType()) {
		case NEW_CLIENT:
			//ci sia un auto o no 
			if(this.nAuto>0) {
				//cliente viene servito -> auto noleggiata
				//1. AGGIORNO MODELLO DEL MONDO 
				this.nAuto--;
				
				//2. AGGIORNO RISULTATI
				this.clienti++;
				
				//3. GENERA NUOVI EVENTI
				double num = Math.random(); //[0,1)
				Duration travel ;
				
				if(num<1.0/3.0) {
					travel = Duration.of(1, ChronoUnit.HOURS);
				} else if(num <2.0/3.0) {
					travel = Duration.of(2, ChronoUnit.HOURS);
				} else 
					travel = Duration.of(3, ChronoUnit.HOURS);
//				System.out.println(travel);
				
				Event nuovo = new Event(e.getTime().plus(travel), EventType.CAR_RETURNED);
				this.queue.add(nuovo);
				
			} else {
				//cliente insoddisfatto
				this.clienti++;
				this.insoddisfatti++;
			}
			break;
			
		case CAR_RETURNED:
			
			//1. AGGIORNO LO STATO
			this.nAuto++;
			
			break;
			
		}
	}
}
