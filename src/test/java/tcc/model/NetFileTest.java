package tcc.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tcc.AmbienteTeste1;
import tcc.ambiente.Fenotipo;
import tcc.ambiente.Genotipo;

public class NetFileTest {

	@Test
	public void test() {
		NetFile netFile = new NetFile();
		netFile.load("   <tl-logic  </tl-logic>    </tl-logic>  ");
		assertEquals(3, netFile.tl_start_index);
		assertEquals(40, netFile.tl_end_index);
	}

	@Test
	public void test1() {
		AmbienteTeste1 ambienteTeste1 = new AmbienteTeste1();

		Genotipo genotipo = new Genotipo();
		genotipo.delay = 0;
		genotipo.plano = 0;
		Fenotipo fenotipo = new Fenotipo(genotipo, ambienteTeste1);
		List<Fenotipo> fenotipos = Arrays.asList(new Fenotipo[] { fenotipo, fenotipo, fenotipo });

		ambienteTeste1.getSimulador().getNetFile().program(fenotipos);

	}

}
