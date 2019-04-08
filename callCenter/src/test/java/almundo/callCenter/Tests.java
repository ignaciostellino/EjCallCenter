package almundo.callCenter;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Test;

import almundo.callCenter.model.Empleado;
import almundo.callCenter.model.Llamada;
import almundo.callCenter.model.Rango;

public class Tests{
	
	
	//Main Test Cases
	
	// Caso base de la consigna, replica de App.java
	@Test
	public void test10LlamadosConcurrentes() throws InterruptedException {
		PriorityBlockingQueue<Empleado> lista = crearListaDeEmpleados();
		Dispatcher dispatcher = new Dispatcher(10,lista);
		
		for(int i = 0; i < 10; i++) {
			dispatcher.dispatchCall(new Llamada());
		}
		
		int cantLlamadasAtendidas = dispatcher.clockOut(15);
		
		assertEquals("Se perdieron llamadas!",10,cantLlamadasAtendidas);
	}
	
	/* 
	 * Se toman 10 llamados instantáneamente al iniciar el test, y los otros 10 se van tomando a medida 
	 * que se liberan tanto empleados como threads. 
	 * */
	@Test
	public void test20LlamadosConcurrentes() throws InterruptedException {
		PriorityBlockingQueue<Empleado> lista = crearListaDeEmpleados();
		Dispatcher dispatcher = new Dispatcher(10,lista);
		
		for(int i = 0; i < 20; i++) {
			dispatcher.dispatchCall(new Llamada());
		}
		
		int cantLlamadasAtendidas = dispatcher.clockOut(25);
		
		assertEquals("Se perdieron llamadas!",20,cantLlamadasAtendidas);
	}
	
	/* 
	 * En este test la lista de empleados sigue siendo de 10, pero el call center tiene capacidad para 
	 * tomar 5 llamados concurrentes.
	 * */
	@Test
	public void testMasEmpleadosQueThreads() throws InterruptedException {
		PriorityBlockingQueue<Empleado> lista = crearListaDeEmpleados();
		Dispatcher dispatcher = new Dispatcher(5,lista);
		
		for(int i = 0; i < 10; i++) {
			dispatcher.dispatchCall(new Llamada());
		}
		
		int llamadasAtendidas = dispatcher.clockOut(30);
		
		assertEquals("Se perdieron llamadas!",10,llamadasAtendidas);
	}
	
	/* 
	 * En este test se envían en total 6 llamadas a un ExecutorService con capacidad para 5, siendo la última 
	 * de una duración superior al timeout especificado en el clockOut. De este modo se puede verificar que esta 
	 * llamada nunca llegará a completarse.
	 * */
	@Test
	public void testLlamadaAbandonada() throws InterruptedException {
		PriorityBlockingQueue<Empleado> lista = crearListaDeEmpleados();
		Dispatcher dispatcher = new Dispatcher(5,lista);
		
		for(int i = 0; i<5; i++) {
			dispatcher.dispatchCall(new Llamada());
		}
		dispatcher.dispatchCall(new Llamada(20));
		
		int llamadasAtendidas = dispatcher.clockOut(10);
		
		assertEquals("Se perdieron llamadas!",6,llamadasAtendidas);
	}
	
	//Test Empleado
	
	// Comparación entre rango de empleados
	@Test
	public void testEmpleadoComparable() {
		Empleado director = new Empleado(Rango.DIRECTOR);
		Empleado operador = new Empleado(Rango.OPERADOR);
		
		int resultado = director.compareTo(operador);
		assertEquals("Comparable no funciona",1,resultado);
	}
	
	// Prueba el funcionamiento de la prioridad de la lista de empleados segun su rango
	@Test
	public void testColaEmpleadosPrioridad() throws InterruptedException {
		Empleado director = new Empleado(Rango.DIRECTOR);
		Empleado operador = new Empleado(Rango.OPERADOR);
		
		PriorityBlockingQueue<Empleado> lista = new PriorityBlockingQueue<Empleado>();
		lista.add(director);
		lista.add(operador);
		
		Empleado unEmpleado = lista.take();
		System.out.println(unEmpleado);
		assertEquals("Agarraste el que no es!",new Integer(0),unEmpleado.getRango().getVal());
	}
	
	// Empleado atiende una llamada
	@Test
	public void testEmpleadoRecibeLlamada() {
		Empleado emp = new Empleado(Rango.DIRECTOR);
		emp.atender(new Llamada(2));
	}
	
	@Test
	public void testConDispatcher() throws InterruptedException {
		PriorityBlockingQueue<Empleado> lista = new PriorityBlockingQueue<Empleado>();
		lista.add(new Empleado(Rango.DIRECTOR));
		lista.add(new Empleado(Rango.OPERADOR));
		Dispatcher dis = new Dispatcher(10,lista);
		dis.dispatchCall(new Llamada(3));
		dis.dispatchCall(new Llamada(5));
		dis.dispatchCall(new Llamada(2));
		dis.dispatchCall(new Llamada(4));
		dis.dispatchCall(new Llamada(7));
		dis.dispatchCall(new Llamada(2));
		dis.dispatchCall(new Llamada(1));
		dis.dispatchCall(new Llamada(4));
		dis.dispatchCall(new Llamada(3));
		dis.clockOut(25);
	}
	
	// Crea una lista de 10 empleados
	private PriorityBlockingQueue<Empleado> crearListaDeEmpleados(){
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