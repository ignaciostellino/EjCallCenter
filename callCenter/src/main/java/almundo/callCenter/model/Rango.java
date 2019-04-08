package almundo.callCenter.model;

public enum Rango {
	//Asigna a cada empleado el valor numerico de la prioridad de su rango.
	OPERADOR(0, "Operador"), SUPERVISOR(1, "Supervisor"), DIRECTOR(2, "Director");
	
	private Integer valor;
	private String descripcion;
	
	public Integer getVal() {
		return this.valor;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	Rango(Integer val, String desc){
		this.valor = val;
		this.descripcion = desc;
	}
}