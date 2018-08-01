package util;

import static org.junit.Assert.*;

import java.util.logging.Level;

import org.junit.Test;

import util.Bitacora;

public class BitacoraTest {

	//@Test(expected=IllegalStateException.class)
	@Test
	public void test() {
		assertTrue(Bitacora.LOG.isLoggable(Level.INFO));
		//fail("Not yet implemented");
	}

}
