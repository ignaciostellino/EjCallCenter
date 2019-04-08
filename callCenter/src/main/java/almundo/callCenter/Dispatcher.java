package almundo.callCenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

import almundo.callCenter.model.Empleado;
import almundo.callCenter.model.Llamada;

public class Dispatcher {
	
	private final static Logger log = Logger.getLogger(Dispatcher.class.getName());

	private ExecutorService ex;
	private PriorityBlockingQueue<Empleado> cola;
	private int llamadasDelDia;
	
	//Inicializador
	//Recibe la cantidad de llamadas que debería poder procesar a la vez,
	//Y el listado de empleados.
	public Dispatcher(int cantidadLlamadosConcurrentes, PriorityBlockingQueue<Empleado> empleados) {
		this.ex = Executors.newFixedThreadPool(cantidadLlamadosConcurrentes);
		this.cola = empleados;
		this.llamadasDelDia = 0;
	}
	
	//Asigna la llamada a un empleado disponible
	//Por la naturaleza de la PriorityBlockingQueue, quedarán en espera los llamados
	//a los que no se les pueda asignar un empleado
	//(por eso el método arroja interruptedException)
	public void dispatchCall(Llamada ll) throws InterruptedException {
		Empleado emp = cola.take();
		ex.submit(asignarLlamada(emp,ll));
	}
	
	//Asignar un empleado a la llamada, y volver a insertarlo en la cola
	private Runnable asignarLlamada(Empleado emp, Llamada ll) {
		Runnable r = () -> {
			llamadasDelDia++;
			emp.atender(ll);
			cola.add(emp);
		};
		return r;
	}
	
	/*
	 * 	metodo para cerrar el día del call center
	 * 	a través de los métodos nativos del ExecutorService
	 *  que permiten esperar a que los threads dejen de trabajar
	 */
	public int clockOut(long timeout) {
		log.info("Orden de terminar el día. Esperando a que se terminen los llamados en curso...");
		ex.shutdown();
        try {
            if (!ex.awaitTermination(timeout, TimeUnit.SECONDS)) {
            		log.warn("Se interrumpió el funcinoamiento del callCenter y quedaron llamadas sin completar.");
                ex.shutdownNow();
            }
        } catch (InterruptedException e) {
        		log.warn("Interrupted!");
        }
		log.info("Día terminado. Atendimos en total "+llamadasDelDia+" llamados!");
		return llamadasDelDia;
	}
}