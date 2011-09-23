package tcc.rotinas;

import org.junit.Test;

import tcc.AmbienteTeste1;
import tcc.ambiente.Ambiente;

public class ProgramacaoSemaforicaTest {

	@Test
	public void test() {
		Ambiente ambiente = new AmbienteTeste1();

		ProgramacaoSemaforica calibragem = new ProgramacaoSemaforica();
		calibragem.setAmbiente(ambiente);
		calibragem.executa();

	}
}
