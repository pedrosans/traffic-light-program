package tcc.rotinas;

import org.junit.Test;

import tcc.AmbienteTeste1;
import tcc.ambiente.Ambiente;

public class CalibragemTest {

	@Test
	public void test() {
		Ambiente ambiente = new AmbienteTeste1();

		Calibragem calibragem = new Calibragem();
		calibragem.setAmbiente(ambiente);
		calibragem.executa();

	}
}
