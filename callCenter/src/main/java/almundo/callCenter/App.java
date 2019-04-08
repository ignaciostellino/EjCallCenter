package almundo.callCenter;

import java.util.concurrent.PriorityBlockingQueue;

import almundo.callCenter.model.Empleado;
import almundo.callCenter.model.Llamada;
import almundo.callCenter.model.Rango;

public class App {
	private static final Integer llamadasConcurrentes = 10;
	
    public static void main( String[] args ) throws InterruptedException {
        System.out.println("================CALL CENTER SIMULATOR================");
        
        //Instanciando estructuras
        
        PriorityBlockingQueue<Empleado> lista = crearListaDeEmpleados();
        Dispatcher dis = new Dispatcher(llamadasConcurrentes, lista);
        
        for(int i = 0; i < llamadasConcurrentes ; i++) {
        		dis.dispatchCall(new Llamada());
        }
        
        dis.clockOut(15);
    }
    
    // Crea una lista de 10 empleados
    private static PriorityBlockingQueue<Empleado> crearListaDeEmpleados() {
    		PriorityBlockingQueue<Empleado> lista = new PriorityBlockingQueue<Empleado>();

    		//7 operadores
    		lista.add(new Empleado(Rango.OPERADOR,"Jose"));
    		lista.add(new Empleado(Rango.OPERADOR,"Juan"));
    		lista.add(new Empleado(Rango.OPERADOR,"Leandro"));
    		lista.add(new Empleado(Rango.OPERADOR,"Gustavo"));
    		lista.add(new Empleado(Rango.OPERADOR,"Martin"));
    		lista.add(new Empleado(Rango.OPERADOR,"Gabriel"));
    		lista.add(new Empleado(Rango.OPERADOR,"Agustina"));
    		
    		//2 supervisores
    		lista.add(new Empleado(Rango.SUPERVISOR,"Florencia"));
    		lista.add(new Empleado(Rango.SUPERVISOR,"Susana"));
    		
    		//1 director
    		lista.add(new Empleado(Rango.DIRECTOR,"Merlin"));
    		
    		return lista;
    }
    
}