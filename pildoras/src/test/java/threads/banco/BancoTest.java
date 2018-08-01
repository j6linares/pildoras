package threads.banco;

import static org.junit.Assert.*;

import org.junit.Test;

public class BancoTest {

	@Test
	public void test() {
		Banco b=new Banco();
		for (int i = 0; i < b.getCuentas().length; i++) {
			EjecucionTransferencias r = new EjecucionTransferencias(b, i, 2000);
			Thread t= new Thread(r);
			t.start();
		}
		assertTrue((b.getSaldo()==200000f));
	}

}
