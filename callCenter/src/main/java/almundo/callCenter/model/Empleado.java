package almundo.callCenter.model;

import org.apache.log4j.Logger;

// Empleado extiende de Comparable para poder usar PriorityBlockingQueue 
public class Empleado implements Comparable<Empleado> {

	private final static Logger log = Logger.getLogger(Empleado.class.getName());
	private Rango rango;
	private String nombre;
	
	public Empleado(Rango rango) {
		this.rango = rango;
	}
	
	public Empleado(Rango rango, String nombre) {
		this.rango = rango;
		this.nombre = nombre;
	}
	
	public void setNombre(String nom) {
		this.nombre = nom;
	}
	
	public String getNombre() {
		if(nombre != null) {
			return nombre;
		}else {
			return "SinNombre";
		}
	}
	
	public Rango getRango() {
		return this.rango;
	}
	
	public int compareTo(Empleado otro) {
		return this.rango.getVal().compareTo(otro.rango.getVal());
	}
	
	// atiende una llamada de duración ll
	public void atender(Llamada ll) {
	
		log.info(this.toString()+" va a atender una llamada de "+ll.getDuracion()+" segundos de duración");
		try {
			Thread.sleep(ll.getDuracion() * 1000);
		} catch (InterruptedException e) {
			log.warn(this.toString()+": Ey! Me apagaron el teléfono!");
		}
		log.info(this.toString()+" acaba de terminar su llamada");
	
	}
	
	@Override
	public String toString() {
		return this.getNombre() + " ("+this.rango.getDescripcion()+")";
	}

}
