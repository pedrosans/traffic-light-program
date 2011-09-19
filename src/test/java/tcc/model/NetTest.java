package tcc.model;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import tcc.AmbienteTeste1;

public class NetTest {

	@Test
	public void test() {
		Net net = Net.load(new File(AmbienteTeste1.path_teste_1 + "example.net.xml"));
		Assert.assertTrue(net.getTlLogics().size() > 0);
	}

}
