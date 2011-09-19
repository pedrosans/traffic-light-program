package tcc.ambiente;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import tcc.AmbienteTeste1;

public class CromossomoTest {

	@Test
	public void test() {
		Cromossomo cromossomo = new Cromossomo();
		AmbienteTeste1 ambienteTeste1 = new AmbienteTeste1();
		cromossomo.genotipos = Arrays.asList(new Genotipo[] {//
				new Genotipo(0, 0), new Genotipo(0, 0), new Genotipo(0, 0) //
				});
		cromossomo.calculaIndeceAdaptabilidade(ambienteTeste1);
		
		assertTrue(cromossomo.indiceAdaptabilidade != 0);
	}

}
