package jdbc.vista;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.zip.DataFormatException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaginacionSDTest {
	
	@Before
	public void inicio(){
		System.out.println("inicio test");
	}
	
	@After
	public void fin() {
		System.out.println("fin test");
	}
	
	@Test
	public void test1() {
		assertTrue(true);
		
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void test2IndexOutOfBoundsException() {
		PaginacionSD paginacion=new PaginacionSD(-1, 10);
		fail();
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void test21IndexOutOfBoundsException() {
		PaginacionSD paginacion=new PaginacionSD(-1, -10);
		fail();
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void test22IndexOutOfBoundsException() {
		PaginacionSD paginacion=new PaginacionSD(1, -10);
		fail();
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void test3Paginacion() {
		PaginacionSD paginacion=new PaginacionSD(89,10);
		paginacion.calcularPaginacion(-3, 7);
		fail();
	}
		
	@Test(expected=IndexOutOfBoundsException.class)
	public void test31Paginacion() {
		PaginacionSD paginacion=new PaginacionSD(89,10);
		paginacion.calcularPaginacion(-3, -7);
		fail();
	}
		
	@Test(expected=IndexOutOfBoundsException.class)
	public void test32Paginacion() {
		PaginacionSD paginacion=new PaginacionSD(89,10);
		paginacion.calcularPaginacion(3, -7);
		fail();
	}
		
	@Test
	public void test3Paginacion3de7() {
		PaginacionSD paginacion=new PaginacionSD(69,10);
		paginacion.calcularPaginacion(3, 7);
		System.out.println("Paginacion de filas:");
		assertTrue("nroFilas", paginacion.getNroFilas()==69);
		for (int f = 1; f <= paginacion.getNroFilas(); f++) {
			paginacion.irAFila(f);
			assertTrue("nroFila", paginacion.getNroFila()==f);
			System.out.println("Fila "+paginacion.getNroFila()+" >>"+(paginacion.toString()));
		}
		
		System.out.println("Paginacion de páginas:");
		assertTrue("nroPaginas", paginacion.getNroPaginas()==(69%3==0?69/3:69/3+1));
		for (int p = 1; p <= paginacion.getNroPaginas(); p++) {
			paginacion.irAPagina(p);
			assertTrue("nroPagina", paginacion.getNroPagina()==p);
			System.out.println("Pag. "+paginacion.getNroPagina()+" >>"+(paginacion.toString()));
			System.out.println("Paginacion de líneas de la página: "+paginacion.getNroPagina());
			for (int l = 1; l <= paginacion.getNroLinxPag(); l++) {
				paginacion.irALinea(l);
				assertTrue("nroLineaPagina", paginacion.getNroLineaPagina()==l);
				System.out.println("\tLin. "+paginacion.getNroLineaPagina()+" >>"+(paginacion.toString()));
				//puede q no esten todas las lineas de la página rellenas
				if (!(paginacion.getNroFila() < paginacion.getNroFilas())){
					assertTrue("nroLineasPagina", paginacion.getNroLineasPagina()==l);
					System.out.println("\tFin de paginacion");
					break;
				}
			}
		}
		System.out.println("Paginacion de bloques:");
		assertTrue("nroBloques", paginacion.getNroBloques()==(69%(7*3)==0?69/(7*3):69/(7*3)+1));
		for (int b = 1; b <= paginacion.getNroBloques(); b++) {
			paginacion.irABloque(b);
			assertTrue("nroBloque", paginacion.getNroBloque()==b);
			System.out.println("Bloq. "+paginacion.getNroBloque()+" >>"+(paginacion.toString()));
			System.out.println("Paginacion de páginas del bloque: "+paginacion.getNroBloque());
			for (int p = 1; p <= paginacion.getNroPaginasBloque(); p++) {
				paginacion.irAPaginaBloque(p);
				assertTrue("nroPaginaBloque", paginacion.getNroPaginaBloque()==p);
				System.out.println("\tPag. "+paginacion.getNroPaginaBloque()+" >>"+(paginacion.toString()));
				System.out.println("\tPaginacion de líneas de la página: "+paginacion.getNroPaginaBloque());
				for (int l = 1; l <= paginacion.getNroLinxPag(); l++) {
					paginacion.irALinea(l);
					assertTrue("nroLineaPagina", paginacion.getNroLineaPagina()==l);
					System.out.println("\t\tLin. "+paginacion.getNroLineaPagina()+" >>"+(paginacion.toString()));
					//puede q no esten todas las lineas de la página rellenas
					if (!(paginacion.getNroFila() < paginacion.getNroFilas())){
						assertTrue("nroLineasPagina", paginacion.getNroLineasPagina()==l);
						System.out.println("\t\tFin de paginacion");
						break;
					}
				}
			}
		}
	}
	
	@Test
	public void test4Paginacion20de10() {
		PaginacionSD paginacion=new PaginacionSD(200,10);
		paginacion.calcularPaginacion(10, 20);
		assertTrue(paginacion.getNroFilas()==200);
		for (int f = 1; f <= paginacion.getNroFilas(); f++) {
			paginacion.irAFila(f);
			assertTrue(paginacion.getNroFila()==f);
		}
		
		assertTrue(paginacion.getNroPaginas()==(200%10==0?200/10:200/10+1));
		for (int p = 1; p <= paginacion.getNroPaginas(); p++) {
			paginacion.irAPagina(p);
			assertTrue(paginacion.getNroPagina()==p);
			for (int l = 1; l <= paginacion.getNroLinxPag(); l++) {
				paginacion.irALinea(l);
				assertTrue(paginacion.getNroLineaPagina()==l);
				//puede q no esten todas las lineas de la página rellenas
				if (!(paginacion.getNroFila() < paginacion.getNroFilas())){
					assertTrue(paginacion.getNroLineasPagina()==l);
					break;
				}
			}
		}
		assertTrue(paginacion.getNroBloques()==(200%(20*10)==0?200/(20*10):200/(20*10)+1));
		for (int b = 1; b <= paginacion.getNroBloques(); b++) {
			paginacion.irABloque(b);
			assertTrue(paginacion.getNroBloque()==b);
			for (int p = 1; p <= paginacion.getNroPaginasBloque(); p++) {
				paginacion.irAPaginaBloque(p);
				assertTrue(paginacion.getNroPaginaBloque()==p);
				for (int l = 1; l <= paginacion.getNroLinxPag(); l++) {
					paginacion.irALinea(l);
					assertTrue(paginacion.getNroLineaPagina()==l);
					//puede q no esten todas las lineas de la página rellenas
					if (!(paginacion.getNroFila() < paginacion.getNroFilas())){
						assertTrue(paginacion.getNroLineasPagina()==l);
						break;
					}
				}
			}
		}
	}
	
	@Test
	public void test5Paginacion2de8() {
		PaginacionSD paginacion=new PaginacionSD(160,10);
		paginacion.calcularPaginacion(2, 8, true);
		System.out.println(paginacion.toString());
		assertTrue(paginacion.getNroFilas()==160);
		assertTrue(paginacion.getNroBloques()==10);
		assertTrue(paginacion.getNroPaginas()==80);
		assertTrue(paginacion.getNroPaginasBloque()==8);
		paginacion.primeraPagina();
		assertTrue(paginacion.getNroPagina()==1);
		paginacion.siguientePagina();
		assertTrue(paginacion.getNroPagina()==2);
		paginacion.paginaAnterior();
		assertTrue(paginacion.getNroPagina()==1);
		paginacion.ultimaPagina();
		assertTrue(paginacion.getNroPagina()==80);
		paginacion.irABloque(2);
		System.out.println(paginacion.toString());
		assertTrue(paginacion.getNroFila()==(2*8)+1);
		paginacion.irAPaginaBloque(2);
		System.out.println(paginacion.toString());
		assertTrue(paginacion.getNroFila()==(2*8)+2+1);
		paginacion.siguienteBloque();
		System.out.println(paginacion.toString());
		assertTrue(paginacion.getNroFila()==(2*8)*2+1);
		paginacion.siguientePaginaBloque();
		System.out.println(paginacion.toString());
		assertTrue(paginacion.getNroFila()==(2*8)*2+2+1);
	}
}
