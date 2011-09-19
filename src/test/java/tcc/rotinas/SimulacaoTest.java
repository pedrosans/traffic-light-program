package tcc.rotinas;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import tcc.AmbienteTeste1;
import tcc.model.NetFile;
import tcc.rotinas.output.StandartDevrivation;

public class SimulacaoTest {
    @Test
    public void test() throws IOException {
        NetFile netFile = new NetFile();
        netFile.load(new File(AmbienteTeste1.path_teste_1 + "example.net.xml"));

        Simulador<StandartDevrivation> simulador = new Simulador(new StandartDevrivation());
        simulador.setNetFile(netFile);
        simulador.setSumoConfigFile(new File(AmbienteTeste1.path_teste_1 + "example.sumo.cfg"));

        simulador.simula();

        Assert.assertNotNull(simulador.getSimulationOutput().getResult());
    }
}
